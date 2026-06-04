import "./Header.css";

export default function Header({ online, aba, setAba, onNovoManga, onNovoAutor }) {
    return (
        <header className="header">
      <span className="header-logo">
        EDITORIAL <span className="header-accent">MANGA</span>
      </span>

            <nav className="header-nav">
                <button
                    className={`nav-btn ${aba === "mangas" ? "active" : ""}`}
                    onClick={() => setAba("mangas")}>
                    Mangas
                </button>
                <button
                    className={`nav-btn ${aba === "autores" ? "active" : ""}`}
                    onClick={() => setAba("autores")}>
                    Autores
                </button>
            </nav>

            <div className="header-right">
                <button className="btn-novo" onClick={aba === "mangas" ? onNovoManga : onNovoAutor}>
                    + Novo {aba === "mangas" ? "Manga" : "Autor"}
                </button>
                <span className="header-status">
          <span className={`status-dot ${online ? "online" : ""}`} />
                    {online ? "API conectada" : "API offline"}
        </span>
            </div>
        </header>
    );
}