import React, { useEffect, useMemo, useState } from 'react';
import Select, { components } from 'react-select';
import { buscarOfertas, obtenerCatalogos } from '../services/OfertasService';

const rsTheme = {
  control: (base, state) => ({
    ...base,
    minHeight: 44,
    borderRadius: 10,
    borderColor: state.isFocused ? '#3b82f6' : '#e5e7eb',
    boxShadow: state.isFocused ? '0 0 0 2px rgba(59,130,246,.15)' : 'none',
    ':hover': { borderColor: '#3b82f6' },
    fontSize: '0.95rem',
    backgroundColor: '#fff',
  }),
  valueContainer: (b) => ({ ...b, padding: '0.25rem 0.5rem' }),
  placeholder: (b) => ({ ...b, color: '#9ca3af' }),
  multiValue: (b) => ({
    ...b,
    backgroundColor: '#e8f1ff',
    borderRadius: 9999,
    paddingRight: 4,
  }),
  multiValueLabel: (b) => ({ ...b, color: '#1d4ed8', fontWeight: 600 }),
  multiValueRemove: (b) => ({
    ...b,
    color: '#1d4ed8',
    ':hover': { background: 'transparent', color: '#1e40af' },
  }),
  menu: (b) => ({
    ...b,
    borderRadius: 10,
    overflow: 'hidden',
    boxShadow: '0 12px 28px rgba(0,0,0,.08)',
  }),
  option: (b, s) => ({
    ...b,
    paddingTop: 10,
    paddingBottom: 10,
    backgroundColor: s.isFocused ? '#f8fafc' : 'white',
    color: s.isDisabled ? '#cbd5e1' : '#111827',
    cursor: s.isDisabled ? 'not-allowed' : 'pointer',
  }),
};

const Option = (props) => {
  const { isSelected, isDisabled, label } = props;
  return (
    <components.Option {...props}>
      <div className="flex items-center gap-2">
        <input
          type="checkbox"
          checked={isSelected}
          readOnly
          disabled={isDisabled}
          className="h-4 w-4"
        />
        <span>{label}</span>
      </div>
    </components.Option>
  );
};

const IndicatorsContainer = (props) => {
  const count = Array.isArray(props.getValue()) ? props.getValue().length : 0;
  return (
    <components.IndicatorsContainer {...props}>
      {count > 0 && (
        <span className="mr-2 inline-flex h-5 min-w-5 px-1.5 items-center justify-center rounded-full bg-emerald-500 text-white text-xs font-semibold">
          {count}
        </span>
      )}
      {props.children}
    </components.IndicatorsContainer>
  );
};

function SelectBNE({ max = 5, valueIds = [], inputId, ...rest }) {
  return (
    <Select
      isMulti
      isSearchable
      closeMenuOnSelect={false}
      hideSelectedOptions={false}
      controlShouldRenderValue={false}
      styles={rsTheme}
      components={{ Option, IndicatorsContainer }}
      isOptionDisabled={(opt) =>
        Number.isFinite(max) &&
        valueIds.length >= max &&
        !valueIds.includes(opt.value)
      }
      noOptionsMessage={() => 'Sin opciones'}
      inputId={inputId}
      {...rest}
    />
  );
}

export default function BuscarOfertasForm() {
  const [filtros, setFiltros] = useState({
    palabraClave: '',
    fechaPublicacion: '',
    region: [],
    comuna: [],
    ocupacion: [],
    grupoEmpleo: [],
    nivelEducativo: [],
    jornadaLaboral: [],
    tipoContrato: [],
    origenOferta: [],
    discapacidad: false,
  });

  const [opciones, setOpciones] = useState({
    region: [],
    comuna: [],
    ocupacion: [],
    grupoEmpleo: [],
    nivelEducativo: [],
    jornadaLaboral: [],
    tipoContrato: [],
    origenOferta: [],
  });

  const [cargandoCatalogos, setCargandoCatalogos] = useState(false);
  const [errorCatalogos, setErrorCatalogos] = useState('');

  const [resultados, setResultados] = useState([]);
  const [buscando, setBuscando] = useState(false);
  const [errorBusqueda, setErrorBusqueda] = useState('');

  const [ofertaSel, setOfertaSel] = useState(null);
  const abrirOferta = (o) => setOfertaSel(o);
  const cerrarOferta = () => setOfertaSel(null);

  const filtrosAplicados = useMemo(() => {
    const chips = [];
    if (filtros.fechaPublicacion)
      chips.push(labelFecha(filtros.fechaPublicacion));

    const fields = [
      'region',
      'comuna',
      'ocupacion',
      'grupoEmpleo',
      'nivelEducativo',
      'jornadaLaboral',
      'tipoContrato',
      'origenOferta',
    ];

    fields.forEach((k) => {
      if (Array.isArray(filtros[k]) && filtros[k].length) {
        chips.push(...filtros[k]);
      }
    });

    if (filtros.discapacidad) chips.push('Ajustes razonables');
    return chips;
  }, [filtros]);

  const opcionesFecha = [
    { value: '', label: 'Fecha de publicación' },
    { value: 'hoy', label: 'Hoy' },
    { value: 'ayer', label: 'Ayer' },
    { value: '<3d', label: 'Menor a 3 días' },
    { value: '<1w', label: 'Menor a 1 semana' },
    { value: '<15d', label: 'Menor a 15 días' },
    { value: '<1m', label: 'Menor a 1 mes' },
    { value: '<2m', label: 'Menor a 2 meses' },
  ];

  useEffect(() => {
    (async () => {
      try {
        setCargandoCatalogos(true);
        setErrorCatalogos('');
        const cat = await obtenerCatalogos();
        const toOpts = (arr = []) => arr.map((v) => ({ value: v, label: v }));
        setOpciones({
          region: toOpts(cat.region),
          comuna: toOpts(cat.comuna),
          ocupacion: toOpts(cat.ocupacion),
          grupoEmpleo: toOpts(cat.grupoEmpleo),
          nivelEducativo: toOpts(cat.nivelEducativo),
          jornadaLaboral: toOpts(cat.jornadaLaboral),
          tipoContrato: toOpts(cat.tipoContrato),
          origenOferta: toOpts(cat.origenOferta),
        });
      } catch (err) {
        console.error(err);
        setErrorCatalogos('No fue posible cargar los catálogos.');
      } finally {
        setCargandoCatalogos(false);
      }
    })();
  }, []);

  const handleMultiChange = (selected, name) =>
    setFiltros((f) => {
      const next = { ...f, [name]: selected?.map((s) => s.value) ?? [] };
      if (name === 'region') next.comuna = [];
      return next;
    });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFiltros((f) => ({
      ...f,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const hoy = new Date();
    const toISO = (d) => d.toISOString().split('T')[0];
    let fechaPublicacion = null;
    switch (filtros.fechaPublicacion) {
      case 'hoy':
        fechaPublicacion = toISO(hoy);
        break;
      case 'ayer': {
        const d = new Date(hoy);
        d.setDate(d.getDate() - 1);
        fechaPublicacion = toISO(d);
        break;
      }
      case '<3d': {
        const d = new Date(hoy);
        d.setDate(d.getDate() - 3);
        fechaPublicacion = toISO(d);
        break;
      }
      case '<1w': {
        const d = new Date(hoy);
        d.setDate(d.getDate() - 7);
        fechaPublicacion = toISO(d);
        break;
      }
      case '<15d': {
        const d = new Date(hoy);
        d.setDate(d.getDate() - 15);
        fechaPublicacion = toISO(d);
        break;
      }
      case '<1m': {
        const d = new Date(hoy);
        d.setMonth(d.getMonth() - 1);
        fechaPublicacion = toISO(d);
        break;
      }
      case '<2m': {
        const d = new Date(hoy);
        d.setMonth(d.getMonth() - 2);
        fechaPublicacion = toISO(d);
        break;
      }
      default:
        fechaPublicacion = null;
    }

    const filtroEnvio = { ...filtros, fechaPublicacion };

    try {
      setBuscando(true);
      setErrorBusqueda('');
      const ofertas = await buscarOfertas(filtroEnvio);
      setResultados(ofertas ?? []);
    } catch (err) {
      console.error(err);
      setErrorBusqueda(err?.message || 'Error al buscar ofertas');
      setResultados([]);
    } finally {
      setBuscando(false);
    }
  };

  const handleReset = () => {
    setFiltros({
      palabraClave: '',
      fechaPublicacion: '',
      region: [],
      comuna: [],
      ocupacion: [],
      grupoEmpleo: [],
      nivelEducativo: [],
      jornadaLaboral: [],
      tipoContrato: [],
      origenOferta: [],
      discapacidad: false,
    });
    setResultados([]);
    setErrorBusqueda('');
  };

  const Skel = () => (
    <div className="h-11 w-full rounded-lg bg-gray-100 animate-pulse" />
  );

  return (
    <div className="p-4 md:p-6">
      <div className="mb-4">
        <div className="inline-flex rounded-xl overflow-hidden border border-gray-200 bg-white shadow-sm">
          <button className="px-4 py-2.5 text-sm font-semibold bg-blue-600 text-white">
            Ofertas BNE
          </button>
          <button className="px-4 py-2.5 text-sm text-gray-400 bg-gray-50 cursor-not-allowed">
            Ofertas Externas
          </button>
        </div>
      </div>

      <form
        onSubmit={handleSubmit}
        className="bg-white rounded-2xl shadow p-4 md:p-5 border border-gray-100"
      >
        <div className="flex gap-3">
          <input
            type="text"
            name="palabraClave"
            placeholder="Profesión, empresa o palabra clave*"
            className="flex-1 h-11 rounded-lg border border-gray-300 px-3 focus:outline-none focus:ring-1 focus:ring-blue-600"
            value={filtros.palabraClave}
            onChange={handleChange}
          />
          <button
            type="submit"
            className="h-11 px-5 rounded-lg bg-blue-600 text-white font-semibold hover:bg-blue-700 transition"
            disabled={buscando}
          >
            {buscando ? (
              'Buscando…'
            ) : (
              <>
                BUSCAR<span className="ml-1">🔎</span>
              </>
            )}
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-3 mt-4">
          <div>
            <label
              htmlFor="select-fecha"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Fecha de publicación
            </label>
            <select
              id="select-fecha"
              name="fechaPublicacion"
              value={filtros.fechaPublicacion}
              onChange={handleChange}
              className="w-full h-11 rounded-lg border border-gray-300 px-3 bg-white"
            >
              {opcionesFecha.map((o) => (
                <option key={o.value} value={o.value}>
                  {o.label}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label
              htmlFor="select-region"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Región
            </label>
            {cargandoCatalogos ? (
              <Skel />
            ) : (
              <SelectBNE
                inputId="select-region"
                options={opciones.region}
                value={opciones.region.filter((o) =>
                  filtros.region.includes(o.value)
                )}
                onChange={(sel) => handleMultiChange(sel, 'region')}
                placeholder="Región"
                valueIds={filtros.region}
              />
            )}
            {filtros.region.length >= 5 && (
              <p className="mt-1 text-xs text-red-600">
                Puedes aplicar máximo 5 criterios por filtro.
              </p>
            )}
          </div>

          <div>
            <label
              htmlFor="select-comuna"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Comunas
            </label>
            {cargandoCatalogos ? (
              <Skel />
            ) : (
              <SelectBNE
                inputId="select-comuna"
                options={opciones.comuna}
                value={opciones.comuna.filter((o) =>
                  filtros.comuna.includes(o.value)
                )}
                onChange={(sel) => handleMultiChange(sel, 'comuna')}
                placeholder={
                  filtros.region.length === 0
                    ? 'Seleccione una región primero'
                    : 'Comunas'
                }
                valueIds={filtros.comuna}
                isDisabled={filtros.region.length === 0}
              />
            )}
            {filtros.comuna.length >= 5 && (
              <p className="mt-1 text-xs text-red-600">
                Puedes aplicar máximo 5 criterios por filtro.
              </p>
            )}
          </div>

          <div>
            <label
              htmlFor="select-ocupacion"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Ocupación
            </label>
            {cargandoCatalogos ? (
              <Skel />
            ) : (
              <SelectBNE
                inputId="select-ocupacion"
                options={opciones.ocupacion}
                value={opciones.ocupacion.filter((o) =>
                  filtros.ocupacion.includes(o.value)
                )}
                onChange={(sel) => handleMultiChange(sel, 'ocupacion')}
                placeholder="Ocupación"
                valueIds={filtros.ocupacion}
                max={Infinity}
              />
            )}
          </div>

          <div>
            <label
              htmlFor="select-grupo"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Grupo de empleo
            </label>
            {cargandoCatalogos ? (
              <Skel />
            ) : (
              <SelectBNE
                inputId="select-grupo"
                options={opciones.grupoEmpleo}
                value={opciones.grupoEmpleo.filter((o) =>
                  filtros.grupoEmpleo.includes(o.value)
                )}
                onChange={(sel) => handleMultiChange(sel, 'grupoEmpleo')}
                placeholder="Grupo de empleo"
                valueIds={filtros.grupoEmpleo}
              />
            )}
            {filtros.grupoEmpleo.length >= 5 && (
              <p className="mt-1 text-xs text-red-600">
                Puedes aplicar máximo 5 criterios por filtro.
              </p>
            )}
          </div>

          <div>
            <label
              htmlFor="select-nivel"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Nivel educativo
            </label>
            {cargandoCatalogos ? (
              <Skel />
            ) : (
              <SelectBNE
                inputId="select-nivel"
                options={opciones.nivelEducativo}
                value={opciones.nivelEducativo.filter((o) =>
                  filtros.nivelEducativo.includes(o.value)
                )}
                onChange={(sel) => handleMultiChange(sel, 'nivelEducativo')}
                placeholder="Nivel educativo"
                valueIds={filtros.nivelEducativo}
              />
            )}
            {filtros.nivelEducativo.length >= 5 && (
              <p className="mt-1 text-xs text-red-600">
                Puedes aplicar máximo 5 criterios por filtro.
              </p>
            )}
          </div>

          <div>
            <label
              htmlFor="select-jornada"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Jornada laboral
            </label>
            {cargandoCatalogos ? (
              <Skel />
            ) : (
              <SelectBNE
                inputId="select-jornada"
                options={opciones.jornadaLaboral}
                value={opciones.jornadaLaboral.filter((o) =>
                  filtros.jornadaLaboral.includes(o.value)
                )}
                onChange={(sel) => handleMultiChange(sel, 'jornadaLaboral')}
                placeholder="Jornada laboral"
                valueIds={filtros.jornadaLaboral}
              />
            )}
            {filtros.jornadaLaboral.length >= 5 && (
              <p className="mt-1 text-xs text-red-600">
                Puedes aplicar máximo 5 criterios por filtro.
              </p>
            )}
          </div>

          <div>
            <label
              htmlFor="select-contrato"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Tipo de contrato
            </label>
            {cargandoCatalogos ? (
              <Skel />
            ) : (
              <SelectBNE
                inputId="select-contrato"
                options={opciones.tipoContrato}
                value={opciones.tipoContrato.filter((o) =>
                  filtros.tipoContrato.includes(o.value)
                )}
                onChange={(sel) => handleMultiChange(sel, 'tipoContrato')}
                placeholder="Tipo de contrato"
                valueIds={filtros.tipoContrato}
              />
            )}
            {filtros.tipoContrato.length >= 5 && (
              <p className="mt-1 text-xs text-red-600">
                Puedes aplicar máximo 5 criterios por filtro.
              </p>
            )}
          </div>

          <div>
            <label
              htmlFor="select-origen"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Origen de la Oferta
            </label>
            {cargandoCatalogos ? (
              <Skel />
            ) : (
              <SelectBNE
                inputId="select-origen"
                options={opciones.origenOferta}
                value={opciones.origenOferta.filter((o) =>
                  filtros.origenOferta.includes(o.value)
                )}
                onChange={(sel) => handleMultiChange(sel, 'origenOferta')}
                placeholder="Origen de la Oferta"
                valueIds={filtros.origenOferta}
              />
            )}
            {filtros.origenOferta.length >= 5 && (
              <p className="mt-1 text-xs text-red-600">
                Puedes aplicar máximo 5 criterios por filtro.
              </p>
            )}
          </div>
        </div>

        <div className="flex items-center justify-between mt-4">
          <label className="inline-flex items-center gap-2 text-sm text-gray-700 select-none">
            <input
              type="checkbox"
              name="discapacidad"
              checked={filtros.discapacidad}
              onChange={handleChange}
              className="h-4 w-4 rounded border-gray-300 text-blue-600 focus:ring-blue-600"
            />
            Puestos de trabajos con ajustes razonables para personas con
            discapacidad
          </label>

          <button
            type="button"
            onClick={handleReset}
            className="text-blue-600 text-sm font-medium hover:underline inline-flex items-center gap-1"
          >
            <span className="inline-block">🔄</span> Reiniciar filtros
          </button>
        </div>

        {filtrosAplicados.length > 0 && (
          <div className="mt-3">
            <p className="text-sm text-blue-700 font-medium">
              Filtros aplicados: {filtrosAplicados.length}
            </p>
            <div className="mt-2 flex flex-wrap gap-2">
              {filtrosAplicados.map((chip, idx) => (
                <span
                  key={`${chip}-${idx}`}
                  className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full bg-blue-50 text-blue-700 text-sm border border-blue-200"
                >
                  {chip}
                  <button
                    type="button"
                    className="hover:text-blue-900"
                    onClick={() => removeChip(chip, filtros, setFiltros)}
                    aria-label={`Quitar ${chip}`}
                  >
                    ✕
                  </button>
                </span>
              ))}
            </div>
          </div>
        )}
      </form>
      {ofertaSel && <OfertaModal oferta={ofertaSel} onClose={cerrarOferta} />}

      <div className="mt-5">
        <div className="flex items-center justify-between">
          <p className="text-gray-700">
            <span className="font-semibold">{resultados.length}</span> ofertas
            de empleo
          </p>
        </div>

        {errorBusqueda && (
          <p className="mt-2 text-sm text-red-600">{errorBusqueda}</p>
        )}

        {!buscando && resultados.length === 0 && !errorBusqueda && (
          <div className="mt-6 rounded-xl border border-dashed p-6 text-center text-gray-500">
            Sin resultados para los filtros actuales.
          </div>
        )}

        <ul className="mt-4 space-y-3">
          {resultados.map((o, idx) => (
            <li
              key={o.id ?? `oferta-${idx}`}
              className="group bg-white rounded-xl border border-gray-100 shadow-sm p-4 flex items-center justify-between hover:shadow-md transition"
            >
              <div>
                <p className="text-blue-700 font-semibold group-hover:underline">
                  {o.titulo}
                </p>
                <p className="text-gray-600 text-sm">
                  {o.empresa} — {o.region}
                  {o.comuna ? `, ${o.comuna}` : ''}
                </p>
                {o.fechaPublicacion && (
                  <p className="text-gray-500 text-xs mt-1">
                    Publicado: {o.fechaPublicacion}
                  </p>
                )}
              </div>
              <button
                type="button"
                onClick={() => abrirOferta(o)}
                className="px-4 h-10 rounded-lg border border-blue-600 text-blue-700 hover:bg-blue-50 font-medium"
              >
                Ver Más
              </button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

function labelFecha(v) {
  const map = {
    hoy: 'Hoy',
    ayer: 'Ayer',
    '<3d': 'Menor a 3 días',
    '<1w': 'Menor a 1 semana',
    '<15d': 'Menor a 15 días',
    '<1m': 'Menor a 1 mes',
    '<2m': 'Menor a 2 meses',
  };
  return map[v] ?? v;
}

function removeChip(chip, filtros, setFiltros) {
  const arrays = [
    'region',
    'comuna',
    'ocupacion',
    'grupoEmpleo',
    'nivelEducativo',
    'jornadaLaboral',
    'tipoContrato',
    'origenOferta',
  ];
  for (const k of arrays) {
    if (filtros[k].includes(chip)) {
      setFiltros({ ...filtros, [k]: filtros[k].filter((x) => x !== chip) });
      return;
    }
  }
  if (labelFecha(filtros.fechaPublicacion) === chip) {
    setFiltros({ ...filtros, fechaPublicacion: '' });
    return;
  }
  if (chip === 'Ajustes razonables') {
    setFiltros({ ...filtros, discapacidad: false });
  }
}

function OfertaModal({ oferta, onClose }) {
  React.useEffect(() => {
    const onKey = (e) => e.key === 'Escape' && onClose();
    window.addEventListener('keydown', onKey);
    return () => window.removeEventListener('keydown', onKey);
  }, [onClose]);

  const Row = ({ label, value }) =>
    value ? (
      <div className="flex gap-3 text-sm">
        <span className="w-44 shrink-0 text-gray-500">{label}</span>
        <span className="text-gray-800">{value}</span>
      </div>
    ) : null;

  return (
    <div
      className="fixed inset-0 z-50"
      aria-modal="true"
      role="dialog"
      aria-labelledby="oferta-title"
    >
      <div className="absolute inset-0 bg-black/40" onClick={onClose} />

      <div className="absolute inset-0 flex items-center justify-center p-4">
        <div className="w-full max-w-3xl rounded-2xl bg-white shadow-xl border border-gray-100">
          <div className="flex items-start justify-between p-5 border-b">
            <div>
              <h2
                id="oferta-title"
                className="text-lg font-semibold text-gray-900"
              >
                {oferta.titulo}
              </h2>
              <p className="text-sm text-gray-500">
                {oferta.empresa}
                {oferta.region ? ` — ${oferta.region}` : ''}
                {oferta.comuna ? `, ${oferta.comuna}` : ''}
              </p>
            </div>
            <button
              onClick={onClose}
              className="h-9 w-9 rounded-full hover:bg-gray-100 flex items-center justify-center text-gray-600"
              aria-label="Cerrar"
              title="Cerrar"
            >
              ✕
            </button>
          </div>

          <div className="p-5 grid grid-cols-1 md:grid-cols-2 gap-4">
            <Row label="Descripción" value={oferta.descripcion} />
            <Row
              label="Fecha de publicación"
              value={oferta.fechaPublicacion || oferta.fecha_publicacion}
            />
            <Row label="Ocupación" value={oferta.ocupacion} />
            <Row
              label="Grupo de empleo"
              value={oferta.grupoEmpleo || oferta.grupo_empleo}
            />
            <Row
              label="Nivel educativo"
              value={oferta.nivelEducativo || oferta.nivel_educativo}
            />
            <Row
              label="Jornada laboral"
              value={oferta.jornadaLaboral || oferta.jornada_laboral}
            />
            <Row
              label="Tipo de contrato"
              value={oferta.tipoContrato || oferta.tipo_contrato}
            />
            <Row
              label="Origen de la oferta"
              value={oferta.origenOferta || oferta.origen_oferta}
            />
            <Row
              label="Ajustes por discapacidad"
              value={
                typeof oferta.discapacidad !== 'undefined'
                  ? oferta.discapacidad
                    ? 'Sí'
                    : 'No'
                  : undefined
              }
            />
          </div>

          <div className="flex items-center justify-end gap-3 p-5 border-t">
            <button
              onClick={onClose}
              className="h-10 px-4 rounded-lg border border-gray-300 text-gray-700 hover:bg-gray-50"
            >
              Cerrar
            </button>
            <button
              className="h-10 px-4 rounded-lg bg-blue-600 text-white font-semibold hover:bg-blue-700"
              onClick={() => {
                onClose();
              }}
            >
              Postular
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
