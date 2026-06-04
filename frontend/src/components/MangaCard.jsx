import "./MangaCard.css";

const COVER_COLORS = [
    ["#e63946","#c1121f"], ["#2d6a4f","#1b4332"],
    ["#023e8a","#03045e"], ["#7b2d8b","#560bad"],
    ["#d4a017","#b5830a"], ["#1d3557","#457b9d"],
    ["#9d0208","#6a040f"], ["#006d77","#004e64"],
];

export function coverColor(id) {
    return COVER_COLORS[(id - 1) % COVER_COLORS.length];
}

export function GenreBadge({ genre }) {
    const map = {
        SHONEN: "badge-shonen", SHOJO: "badge-shojo",
        SEINEN: "badge-seinen", JOSEI: "badge-josei",
        ISEKAI: "badge-isekai",
    };
    const cls = map[genre] || "badge-default";
    const label = genre?.replace("_", " ").toLowerCase()
        .replace(/^\w/, c => c.toUpperCase());
    return <span className={`badge ${cls}`}>{label}</span>;
}

export function StatusBadge({ status }) {
    const labels = {
        EM_PUBLICACAO: "Em publicação", CONCLUIDO: "Concluído",
        PAUSADO: "Pausado", CANCELADO: "Cancelado",
    };
    const cls = "status-" + status?.toLowerCase();
    return <span className={`status-badge ${cls}`}>{labels[status] || status}</span>;
}

export default function MangaCard({ manga, onClick, onEditar, onDeletar }) {
    const [c1, c2] = coverColor(manga.id || 1);
    const autorNome = manga.autor?.nome || "Autor desconhecido";
    const titulo = manga.titulo?.length > 18
        ? manga.titulo.substring(0, 16) + "…" : manga.titulo;

    return (
        <div className="card">
            <div className="card-cover"
                 style={{ background: `linear-gradient(160deg, ${c1}, ${c2})` }}
                 onClick={() => onClick(manga)}>
                <span className="card-cover-label">{titulo}</span>
            </div>
            <div className="card-body">
                <div className="card-title">{manga.titulo}</div>
                <div className="card-author">{autorNome}</div>
                <div className="card-footer">
                    <GenreBadge genre={manga.genero} />
                    <StatusBadge status={manga.status} />
                </div>
                <div className="card-actions">
                    <button className="btn-edit" onClick={() => onEditar(manga)}>Editar</button>
                    <button className="btn-delete" onClick={() => onDeletar(manga.id)}>Deletar</button>
                </div>
            </div>
        </div>
    );
}