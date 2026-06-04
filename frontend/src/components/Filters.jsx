import "./Filters.css";

const GENEROS = [
    ["", "Todos"], ["SHONEN","Shonen"], ["SHOJO","Shojo"],
    ["SEINEN","Seinen"], ["JOSEI","Josei"], ["ISEKAI","Isekai"],
    ["SLICE_OF_LIFE","Slice of Life"], ["HORROR","Horror"],
    ["ROMANCE","Romance"], ["FANTASY","Fantasy"],
    ["ACTION","Action"], ["SPORTS","Sports"],
];

const STATUS_LIST = [
    ["", "Todos"], ["EM_PUBLICACAO","Em publicação"],
    ["CONCLUIDO","Concluído"], ["PAUSADO","Pausado"], ["CANCELADO","Cancelado"],
];

export default function Filters({ genero, setGenero, status, setStatus, onFilter, onReset }) {
    return (
        <div className="filters">
            <label>Gênero</label>
            <select value={genero} onChange={e => setGenero(e.target.value)}>
                {GENEROS.map(([v, l]) => <option key={v} value={v}>{l}</option>)}
            </select>

            <label>Status</label>
            <select value={status} onChange={e => setStatus(e.target.value)}>
                {STATUS_LIST.map(([v, l]) => <option key={v} value={v}>{l}</option>)}
            </select>

            <button className="btn-filter" onClick={onFilter}>Filtrar</button>
            <button className="btn-reset" onClick={onReset}>Limpar</button>
        </div>
    );
}