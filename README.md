Editorial de Mangás
Aplicação web para gestão de uma editorial de mangás, desenvolvida como projeto integrado
da disciplina de Engenharia de Softwares Escaláveis no Instituto Infnet.

Sobre o projeto
O sistema permite gerir autores e obras de mangá, incluindo controlo de status de
publicação e histórico de alterações. A aplicação foi construída seguindo os princípios
do Domain-Driven Design (DDD) com arquitectura em camadas e uma interface React que
consome a API REST directamente.

Funcionalidades

*Autores*
- Cadastro, listagem, edição e remoção de autores
- Busca por nome

*Mangás*
- Cadastro, listagem, edição e remoção de obras
- Filtro por género, status e autor
- Atualização isolada de status de publicação
- Histórico de mudanças de status com timestamp

*Interface*
- Catálogo visual com cards por obra
- Navegação entre secções sem mudança de URL
- Formulários modais para cadastro e edição
- Indicador de conexão com a API

<img width="661" height="692" alt="uml drawio" src="https://github.com/user-attachments/assets/d2078ef6-ccf9-4b6a-a26b-323dbe93d500" />


Endpoints principais

| GET | /api/autores | Listar autores |

| POST | /api/autores | Criar autor |

| PUT | /api/autores/{id} | Atualizar autor |

| DELETE | /api/autores/{id} | Remover autor |

| GET | /api/mangas | Listar mangás (aceita ?genero= ?status= ?autorId=) |

| POST | /api/mangas | Criar manga |

| PUT | /api/mangas/{id} | Atualizar manga |

| PATCH | /api/mangas/{id}/status | Atualizar status |

| DELETE | /api/mangas/{id} | Remover manga |

| GET | /api/historico | Histórico de status (aceita ?mangaId= ?autorId=) |


Como executar
*Backend*
1. Abre o projeto no IntelliJ
2. Corre `Tp1EditorialMangasSofiaCastroApplication.java`
3. API disponível em `http://localhost:8080`
4. Console H2 disponível em `http://localhost:8080/h2-console`

*Frontend*
```bash
cd frontend
npm install
npm run dev
```
Interface disponível em `http://localhost:5173`

Testes

```bash
# Correr todos os testes no IntelliJ
# Botão direito na pasta test/ → Run All Tests
```

Cobertura actual:
- `AutorRepositoryTest` testes de persistência de autores
- `MangaRepositoryTest` testes de filtros por género, status e autor
- `MangaStatusHistoricoRepositoryTest` testes de histórico
- `AutorServiceTest` testes de lógica de negócio de autores
- `MangaServiceTest` testes de lógica de negócio de mangás

