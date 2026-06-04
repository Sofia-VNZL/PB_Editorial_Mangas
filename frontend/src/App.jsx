import { useState, useEffect } from "react";
import Header from "./components/Header";
import Filters from "./components/Filters";
import MangaGrid from "./components/MangaGrid";
import AutorGrid from "./components/AutorGrid";
import Modal from "./components/Modal";
import FormModal from "./components/FormModal";
import AutorFormModal from "./components/AutorFormModal";
import "./App.css";

const API = "http://localhost:8080/api";

export default function App() {
  const [aba, setAba] = useState("mangas");

  const [mangas, setMangas] = useState([]);
  const [autores, setAutores] = useState([]);
  const [loading, setLoading] = useState(true);
  const [online, setOnline] = useState(false);

  const [genero, setGenero] = useState("");
  const [status, setStatus] = useState("");

  const [detalhe, setDetalhe] = useState(null);
  const [formManga, setFormManga] = useState(null);
  const [formAutor, setFormAutor] = useState(null);

  async function loadMangas(params = {}) {
    setLoading(true);
    const qs = new URLSearchParams(
        Object.fromEntries(Object.entries(params).filter(([, v]) => v))
    ).toString();
    try {
      const r = await fetch(`${API}/mangas${qs ? "?" + qs : ""}`);
      setMangas(await r.json());
      setOnline(true);
    } catch {
      setOnline(false);
      setMangas([]);
    } finally {
      setLoading(false);
    }
  }

  async function loadAutores() {
    try {
      const r = await fetch(`${API}/autores`);
      setAutores(await r.json());
    } catch {
      setAutores([]);
    }
  }

  useEffect(() => { loadMangas(); loadAutores(); }, []);

  async function deletarManga(id) {
    if (!confirm("Deletar este manga?")) return;
    await fetch(`${API}/mangas/${id}`, { method: "DELETE" });
    loadMangas();
  }

  async function deletarAutor(id) {
    if (!confirm("Deletar este autor?")) return;
    await fetch(`${API}/autores/${id}`, { method: "DELETE" });
    loadAutores();
  }

  return (
      <div>
        <Header
            online={online} aba={aba} setAba={setAba}
            onNovoManga={() => setFormManga({})}
            onNovoAutor={() => setFormAutor({})}
        />

        {aba === "mangas" && (
            <>
              <Filters
                  genero={genero} setGenero={setGenero}
                  status={status} setStatus={setStatus}
                  onFilter={() => loadMangas({ genero, status })}
                  onReset={() => { setGenero(""); setStatus(""); loadMangas(); }}
              />
              <MangaGrid
                  mangas={mangas} loading={loading} online={online}
                  onSelect={setDetalhe}
                  onEditar={(m) => setFormManga(m)}
                  onDeletar={deletarManga}
              />
            </>
        )}

        {aba === "autores" && (
            <AutorGrid
                autores={autores}
                onEditar={(a) => setFormAutor(a)}
                onDeletar={deletarAutor}
            />
        )}

        <Modal manga={detalhe} onClose={() => setDetalhe(null)} />

        <FormModal
            manga={formManga}
            autores={autores}
            onClose={() => setFormManga(null)}
            onSaved={() => { setFormManga(null); loadMangas(); }}
        />

        <AutorFormModal
            autor={formAutor}
            onClose={() => setFormAutor(null)}
            onSaved={() => { setFormAutor(null); loadAutores(); }}
        />
      </div>
  );
}