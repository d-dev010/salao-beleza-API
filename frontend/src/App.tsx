import { useEffect, useState } from 'react'
import './App.css'

type View = 'public' | 'login' | 'usuarios' | 'servicos' | 'agendamentos'

const API_URL = 'http://localhost:8080'

function App() {
  const [view, setView] = useState<View>('public')
  const [token, setToken] = useState<string | null>(null)

  useEffect(() => {
    const saved = localStorage.getItem('token')
    if (saved) {
      setToken(saved)
      setView('usuarios')
    } else {
      setView('public')
    }
  }, [])

  function handleLoginSuccess(jwt: string) {
    localStorage.setItem('token', jwt)
    setToken(jwt)
    setView('usuarios')
  }

  function handleLogout() {
    localStorage.removeItem('token')
    setToken(null)
    setView('public')
  }

  return (
    <div className="app">
      <header className="app-header">
        <h1>Salão de Beleza</h1>
        <nav className="app-nav">
          {token ? (
            <>
              <button onClick={() => setView('usuarios')}>Usuários</button>
              <button onClick={() => setView('servicos')}>Serviços</button>
              <button onClick={() => setView('agendamentos')}>Agendamentos</button>
              <button className="logout" onClick={handleLogout}>
                Sair
              </button>
            </>
          ) : (
            <button onClick={() => setView('login')}>Área administrativa</button>
          )}
        </nav>
      </header>

      <main className="app-main">
        {!token && view === 'public' && <PublicHome />}
        {!token && view === 'login' && <LoginPage onSuccess={handleLoginSuccess} />}
        {token && view === 'usuarios' && <UsuariosPage token={token} />}
        {token && view === 'servicos' && <ServicosPage token={token} />}
        {token && view === 'agendamentos' && <AgendamentosPage token={token} />}
      </main>
    </div>
  )
}

function PublicHome() {
  const [servicos, setServicos] = useState<Servico[]>([])

  useEffect(() => {
    ;(async () => {
      try {
        const res = await fetch(`${API_URL}/api/v1/servicos`)
        if (!res.ok) return
        const data = (await res.json()) as Servico[]
        setServicos(data)
      } catch {
        // ignora erro na home publica
      }
    })()
  }, [])

  return (
    <section className="grid">
      <div className="card">
        <h2>Bem-vindo ao nosso salão</h2>
        <p style={{ fontSize: '0.95rem', color: '#e5e7eb', lineHeight: 1.6 }}>
          Consulte os serviços disponíveis e entre em contato com a equipe para agendar
          o melhor horário para você.
        </p>
        <p style={{ marginTop: '1rem', fontSize: '0.9rem', color: '#cbd5f5' }}>
          Telefone / WhatsApp: <strong>(00) 00000-0000</strong>
        </p>
        <p style={{ fontSize: '0.9rem', color: '#cbd5f5' }}>
          Endereço: <strong>Sua rua, 123 - Sua cidade</strong>
        </p>
      </div>

      <div className="card">
        <h2>Serviços</h2>
        {servicos.length === 0 && <p>Em breve lista de serviços aqui.</p>}
        <ul className="list">
          {servicos.map((s) => (
            <li key={s.id}>
              <strong>{s.nome}</strong>
              <span>{s.duracao} min</span>
              {s.descricao && <span>{s.descricao}</span>}
            </li>
          ))}
        </ul>
      </div>
    </section>
  )
}

type LoginPageProps = {
  onSuccess: (token: string) => void
}

function LoginPage({ onSuccess }: LoginPageProps) {
  const [email, setEmail] = useState('admin@salao.com')
  const [senha, setSenha] = useState('admin123')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setLoading(true)
    setError(null)
    try {
      const res = await fetch(`${API_URL}/api/v1/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, senha }),
      })
      if (!res.ok) {
        throw new Error('Credenciais inválidas')
      }
      const data = (await res.json()) as { token: string }
      onSuccess(data.token)
    } catch (err: any) {
      setError(err.message ?? 'Erro ao fazer login')
    } finally {
      setLoading(false)
    }
  }

  return (
    <section className="card card-centered">
      <h2>Login</h2>
      <form onSubmit={handleSubmit} className="form">
        <label>
          Email
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </label>
        <label>
          Senha
          <input
            type="password"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required
          />
        </label>
        {error && <p className="error">{error}</p>}
        <button type="submit" disabled={loading}>
          {loading ? 'Entrando...' : 'Entrar'}
        </button>
      </form>
    </section>
  )
}

type Usuario = {
  id: number
  nome: string
  email: string
  role: string
}

type UsuariosPageProps = {
  token: string
}

function UsuariosPage({ token }: UsuariosPageProps) {
  const [usuarios, setUsuarios] = useState<Usuario[]>([])
  const [nome, setNome] = useState('')
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [role, setRole] = useState('ADMIN')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  async function carregar() {
    setLoading(true)
    setError(null)
    try {
      const res = await fetch(`${API_URL}/api/v1/usuarios`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      if (!res.ok) throw new Error('Erro ao buscar usuários')
      const data = (await res.json()) as Usuario[]
      setUsuarios(data)
    } catch (err: any) {
      setError(err.message ?? 'Erro ao buscar usuários')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    void carregar()
  }, [])

  async function handleCreate(e: React.FormEvent) {
    e.preventDefault()
    setLoading(true)
    setError(null)
    try {
      const res = await fetch(`${API_URL}/api/v1/usuarios`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ nome, email, senha, role }),
      })
      if (!res.ok) throw new Error('Erro ao criar usuário')
      setNome('')
      setEmail('')
      setSenha('')
      await carregar()
    } catch (err: any) {
      setError(err.message ?? 'Erro ao criar usuário')
    } finally {
      setLoading(false)
    }
  }

  return (
    <section className="grid">
      <div className="card">
        <h2>Novo usuário</h2>
        <form onSubmit={handleCreate} className="form">
          <label>
            Nome
            <input value={nome} onChange={(e) => setNome(e.target.value)} required />
          </label>
          <label>
            Email
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </label>
          <label>
            Senha
            <input
              type="password"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              required
            />
          </label>
          <label>
            Papel
            <select value={role} onChange={(e) => setRole(e.target.value)}>
              <option value="ADMIN">ADMIN</option>
              <option value="CABELEREIRO">CABELEREIRO</option>
            </select>
          </label>
          {error && <p className="error">{error}</p>}
          <button type="submit" disabled={loading}>
            {loading ? 'Salvando...' : 'Salvar'}
          </button>
        </form>
      </div>

      <div className="card">
        <h2>Usuários cadastrados</h2>
        {loading && usuarios.length === 0 && <p>Carregando...</p>}
        {!loading && usuarios.length === 0 && <p>Nenhum usuário encontrado.</p>}
        <ul className="list">
          {usuarios.map((u) => (
            <li key={u.id}>
              <strong>{u.nome}</strong>
              <span>{u.email}</span>
              <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                <span className="tag">{u.role}</span>
                <button
                  type="button"
                  style={{ paddingInline: '0.6rem', fontSize: '0.75rem' }}
                  onClick={async () => {
                    if (!confirm('Deseja realmente deletar este usuário?')) return
                    try {
                      const res = await fetch(`${API_URL}/api/v1/usuarios/${u.id}`, {
                        method: 'DELETE',
                        headers: { Authorization: `Bearer ${token}` },
                      })
                      if (!res.ok) {
                        const text = await res.text()
                        throw new Error(text || 'Erro ao deletar usuário')
                      }
                      await carregar()
                    } catch (err: any) {
                      alert(err.message ?? 'Erro ao deletar usuário')
                    }
                  }}
                >
                  Deletar
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </section>
  )
}

type Servico = {
  id: number
  nome: string
  duracao: number
  descricao?: string
}

type ServicosPageProps = {
  token: string
}

function ServicosPage({ token }: ServicosPageProps) {
  const [servicos, setServicos] = useState<Servico[]>([])
  const [nome, setNome] = useState('')
  const [duracao, setDuracao] = useState(30)
  const [descricao, setDescricao] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  async function carregar() {
    setLoading(true)
    setError(null)
    try {
      const res = await fetch(`${API_URL}/api/v1/servicos`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      if (!res.ok) throw new Error('Erro ao buscar serviços')
      const data = (await res.json()) as Servico[]
      setServicos(data)
    } catch (err: any) {
      setError(err.message ?? 'Erro ao buscar serviços')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    void carregar()
  }, [])

  async function handleCreate(e: React.FormEvent) {
    e.preventDefault()
    setLoading(true)
    setError(null)
    try {
      const res = await fetch(`${API_URL}/api/v1/servicos`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ nome, duracao, descricao }),
      })
      if (!res.ok) throw new Error('Erro ao criar serviço')
      setNome('')
      setDuracao(30)
      setDescricao('')
      await carregar()
    } catch (err: any) {
      setError(err.message ?? 'Erro ao criar serviço')
    } finally {
      setLoading(false)
    }
  }

  return (
    <section className="grid">
      <div className="card">
        <h2>Novo serviço</h2>
        <form onSubmit={handleCreate} className="form">
          <label>
            Nome
            <input value={nome} onChange={(e) => setNome(e.target.value)} required />
          </label>
          <label>
            Duração (minutos)
            <input
              type="number"
              min={15}
              max={480}
              value={duracao}
              onChange={(e) => setDuracao(Number(e.target.value))}
              required
            />
          </label>
          <label>
            Descrição
            <textarea
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
              rows={3}
            />
          </label>
          {error && <p className="error">{error}</p>}
          <button type="submit" disabled={loading}>
            {loading ? 'Salvando...' : 'Salvar'}
          </button>
        </form>
      </div>

      <div className="card">
        <h2>Serviços cadastrados</h2>
        {loading && servicos.length === 0 && <p>Carregando...</p>}
        {!loading && servicos.length === 0 && <p>Nenhum serviço encontrado.</p>}
        <ul className="list">
          {servicos.map((s) => (
            <li key={s.id}>
              <strong>{s.nome}</strong>
              <span>{s.duracao} min</span>
              {s.descricao && <span>{s.descricao}</span>}
              <div style={{ marginTop: '0.25rem' }}>
                <button
                  type="button"
                  style={{ paddingInline: '0.6rem', fontSize: '0.75rem' }}
                  onClick={async () => {
                    if (!confirm('Deseja realmente deletar este serviço?')) return
                    try {
                      const res = await fetch(`${API_URL}/api/v1/servicos/${s.id}`, {
                        method: 'DELETE',
                        headers: { Authorization: `Bearer ${token}` },
                      })
                      if (!res.ok) {
                        const text = await res.text()
                        throw new Error(text || 'Erro ao deletar serviço')
                      }
                      await carregar()
                    } catch (err: any) {
                      alert(err.message ?? 'Erro ao deletar serviço')
                    }
                  }}
                >
                  Deletar
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </section>
  )
}

type Agendamento = {
  id: number
  dataHora: string
  nomeCliente?: string
  nomeUsuario: string
  nomeServico: string
  status: string
}

type AgendamentosPageProps = {
  token: string
}

function AgendamentosPage({ token }: AgendamentosPageProps) {
  const [agendamentos, setAgendamentos] = useState<Agendamento[]>([])
  const [servicos, setServicos] = useState<Servico[]>([])
  const [data, setData] = useState('')
  const [hora, setHora] = useState('')
  const [nomeCliente, setNomeCliente] = useState('')
  const [servicoId, setServicoId] = useState<number | ''>('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  async function carregar() {
    setLoading(true)
    setError(null)
    try {
      const res = await fetch(`${API_URL}/api/v1/agendamentos`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      if (!res.ok) throw new Error('Erro ao buscar agendamentos')
      const data = (await res.json()) as Agendamento[]
      setAgendamentos(data)
    } catch (err: any) {
      setError(err.message ?? 'Erro ao buscar agendamentos')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    void carregar()
    ;(async () => {
      try {
        const res = await fetch(`${API_URL}/api/v1/servicos`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        if (!res.ok) throw new Error()
        const data = (await res.json()) as Servico[]
        setServicos(data)
      } catch {
        // silencioso na tela de agendamentos
      }
    })()
  }, [])

  async function handleCreate(e: React.FormEvent) {
    e.preventDefault()
    if (!data || !hora) {
      setError('Informe data e hora')
      return
    }

    const dataHora = `${data}T${hora}:00`
    setLoading(true)
    setError(null)
    try {
      const body = {
        dataHora,
        nomeCliente: nomeCliente || null,
        usuarioId: null,
        servicoId,
      }
      const res = await fetch(`${API_URL}/api/v1/agendamentos`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(body),
      })
      if (!res.ok) {
        const text = await res.text()
        throw new Error(text || 'Erro ao criar agendamento')
      }
      setData('')
      setHora('')
      setNomeCliente('')
      setServicoId('')
      await carregar()
    } catch (err: any) {
      setError(err.message ?? 'Erro ao criar agendamento')
    } finally {
      setLoading(false)
    }
  }

  return (
    <section className="grid">
      <div className="card">
        <h2>Novo agendamento</h2>
        <form onSubmit={handleCreate} className="form">
          <label>
            Data
            <input
              type="date"
              value={data}
              onChange={(e) => setData(e.target.value)}
              required
            />
          </label>
          <label>
            Hora
            <input
              type="time"
              value={hora}
              onChange={(e) => setHora(e.target.value)}
              required
            />
          </label>
          <label>
            Nome do cliente
            <input
              value={nomeCliente}
              onChange={(e) => setNomeCliente(e.target.value)}
              placeholder="Opcional"
            />
          </label>
          <label>
            Serviço
            <select
              value={servicoId}
              onChange={(e) =>
                setServicoId(e.target.value === '' ? '' : Number(e.target.value))
              }
              required
            >
              <option value="">Selecione um serviço</option>
              {servicos.map((s) => (
                <option key={s.id} value={s.id}>
                  {s.nome} ({s.duracao} min)
                </option>
              ))}
            </select>
          </label>
          {error && <p className="error">{error}</p>}
          <button type="submit" disabled={loading}>
            {loading ? 'Salvando...' : 'Salvar'}
          </button>
        </form>
      </div>

      <div className="card">
        <h2>Agendamentos</h2>
        {loading && agendamentos.length === 0 && <p>Carregando...</p>}
        {!loading && agendamentos.length === 0 && <p>Nenhum agendamento encontrado.</p>}
        <ul className="list">
          {agendamentos.map((a) => (
            <li key={a.id}>
              <strong>
                {a.nomeServico} - {a.nomeUsuario}
              </strong>
              {a.nomeCliente && <span>Cliente: {a.nomeCliente}</span>}
              <span>{new Date(a.dataHora).toLocaleString()}</span>
              <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                <span className="tag">{a.status}</span>
                <button
                  type="button"
                  style={{ paddingInline: '0.6rem', fontSize: '0.75rem' }}
                  onClick={async () => {
                    if (!confirm('Deseja realmente deletar este agendamento?')) return
                    try {
                      const res = await fetch(`${API_URL}/api/v1/agendamentos/${a.id}`, {
                        method: 'DELETE',
                        headers: {
                          Authorization: `Bearer ${token}`,
                        },
                      })
                      if (!res.ok) {
                        const text = await res.text()
                        throw new Error(text || 'Erro ao deletar agendamento')
                      }
                      await carregar()
                    } catch (err: any) {
                      alert(err.message ?? 'Erro ao deletar agendamento')
                    }
                  }}
                >
                  Deletar
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </section>
  )
}

export default App
