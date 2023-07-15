import React, { useState, useEffect } from "react";
import { Container, Header, Popup, Form, Button, Checkbox, Table, Modal } from "semantic-ui-react";
import axios from "axios";
import "./App.css";

const VeiculoForm = ({ open, onClose, onCreateVeiculo, veiculoEditado, setVeiculoEditado }) => {
  const [veiculo, setVeiculo] = useState({
    veiculo: "",
    marca: "",
    ano: "",
    descricao: "",
    cor: "",
    vendido: false,
  });

  useEffect(() => {
    if (veiculoEditado) {
      setVeiculo(veiculoEditado);
    } else {
      setVeiculo({
        veiculo: "",
        marca: "",
        ano: "",
        descricao: "",
        cor: "",
        vendido: false,
      });
    }
  }, [veiculoEditado]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setVeiculo((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (veiculoEditado) {
      axios
        .put(`http://localhost:8080/api/veiculos/${veiculoEditado.id}`, veiculo)
        .then(() => {
          alert("Veículo editado com sucesso!");
          setVeiculoEditado(null);
          onCreateVeiculo(); // Chama a função de atualização dos veículos
          onClose();
        })
        .catch((error) => {
          alert("Erro ao editar veículo.");
          console.error(error);
        });
    } else {
      axios
        .post("http://localhost:8080/api/veiculos", veiculo)
        .then(() => {
          alert("Veículo criado com sucesso!");
          setVeiculoEditado(null);
          onCreateVeiculo(); // Chama a função de atualização dos veículos
          onClose();
        })
        .catch((error) => {
          alert("Erro ao criar veículo.");
          console.error(error);
        });
    }
  };
  
    return (
      <Popup open={open} onClose={onClose} position="center center">
        <div style={{ padding: "1rem", maxWidth: "400px", margin: "0 auto" }}>
          <Header as="h3">{veiculoEditado ? "Editar Veículo" : "Criar Veículo"}</Header>
          <Form onSubmit={handleSubmit}>
          <Form.Field>
            <label>Veiculo</label>
            <input
              placeholder="Veiculo"
              name="veiculo"
              value={veiculo.veiculo}
              onChange={handleInputChange}
            />
          </Form.Field>
          <Form.Field>
            <label>Marca</label>
            <input
              placeholder="Marca"
              name="marca"
              value={veiculo.marca}
              onChange={handleInputChange}
            />
          </Form.Field>
          <Form.Field>
            <label>Ano</label>
            <input
              placeholder="Ano"
              name="ano"
              value={veiculo.ano}
              onChange={handleInputChange}
            />
          </Form.Field>
          <Form.Field>
            <label>Descrição</label>
            <input
              placeholder="Descrição"
              name="descricao"
              value={veiculo.descricao}
              onChange={handleInputChange}
            />
          </Form.Field>
          <Form.Field>
            <label>Cor</label>
            <input
              placeholder="Cor"
              name="cor"
              value={veiculo.cor}
              onChange={handleInputChange}
            />
          </Form.Field>
          <Form.Field>
            <Checkbox
              label="Vendido"
              name="vendido"
              checked={veiculo.vendido}
              onChange={() =>
                setVeiculo((prevState) => ({
                  ...prevState,
                  vendido: !prevState.vendido,
                }))
              }
            />
          </Form.Field>
          <Button type="submit" primary>
            {veiculoEditado ? "Salvar" : "Criar"}
          </Button>
          </Form>
        </div>
      </Popup>
    );
  };

const App = () => {
  const [veiculos, setVeiculos] = useState([]);
  const [veiculosCriadosUltimaSemana, setVeiculosCriadosUltimaSemana] = useState([]);
  const [distribuicaoPorDecada, setDistribuicaoPorDecada] = useState({});
  const [veiculosPorFabricante, setVeiculosPorFabricante] = useState([]);
  const [veiculosNaoVendidos, setVeiculosNaoVendidos] = useState([]);
  const [filtroMarca, setFiltroMarca] = useState("");
  const [filtroAno, setFiltroAno] = useState("");
  const [filtroCor, setFiltroCor] = useState("");
  const [veiculoEditado, setVeiculoEditado] = useState(null);
  const [modalAberto, setModalAberto] = useState(false);

  useEffect(() => {
    carregarVeiculos();
  }, []);

  const atualizarDados = () => {
    carregarVeiculos();
    carregarVeiculosCriadosUltimaSemana();
    carregarDistribuicaoPorDecada();
    carregarVeiculosPorFabricante();
    carregarVeiculosNaoVendidos();
  };

  const carregarVeiculos = () => {
    const params = {};

    if (filtroMarca) {
      params.marca = filtroMarca;
    }

    if (filtroAno) {
      params.ano = filtroAno;
    }

    if (filtroCor) {
      params.cor = filtroCor;
    }

    axios
      .get("http://localhost:8080/api/veiculos", { params })
      .then((response) => {
        setVeiculos(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const carregarVeiculosCriadosUltimaSemana = () => {
    axios
      .get("http://localhost:8080/api/veiculos/veiculosCriadosUltimaSemana")
      .then((response) => {
        setVeiculosCriadosUltimaSemana(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const carregarDistribuicaoPorDecada = () => {
    axios
      .get("http://localhost:8080/api/veiculos/distribuicaoPorDecada")
      .then((response) => {
        setDistribuicaoPorDecada(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const carregarVeiculosPorFabricante = () => {
    axios
      .get("http://localhost:8080/api/veiculos/buscaVeiculosPorFabricante")
      .then((response) => {
        setVeiculosPorFabricante(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const carregarVeiculosNaoVendidos = () => {
    axios
      .get("http://localhost:8080/api/veiculos/buscaVeiculosNaoVendidos")
      .then((response) => {
        setVeiculosNaoVendidos(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleVeiculoCriado = () => {
    atualizarDados();
  };

  const handleFiltrar = () => {
    carregarVeiculos();
  };

  const handleAbrirModal = () => {
    setModalAberto(true);
  };

  const handleFecharModal = () => {
    setVeiculoEditado(null);
    setModalAberto(false);
  };

  const handleEditarVeiculo = (veiculo) => {
    setVeiculoEditado(veiculo);
    setModalAberto(true);
  };

  const handleEditarSubmit = (event) => {
    event.preventDefault();
    axios
      .put(`http://localhost:8080/api/veiculos/${veiculoEditado.id}`, veiculoEditado)
      .then(() => {
        alert("Veículo editado com sucesso!");
        setVeiculoEditado(null);
        atualizarDados();
        setModalAberto(false);
      })
      .catch((error) => {
        alert("Erro ao editar veículo.");
        console.error(error);
      });
  };

  const handleDeletarVeiculo = (id) => {
    axios
      .delete(`http://localhost:8080/api/veiculos/${id}`)
      .then(() => {
        alert("Veículo deletado com sucesso!");
        atualizarDados();
      })
      .catch((error) => {
        alert("Erro ao deletar veículo.");
        console.error(error);
      });
  };

  const handleVendaVeiculo = (id, vendido) => {
    const novoStatusVenda = !vendido;
    axios
      .patch(`http://localhost:8080/api/veiculos/${id}`, { vendido: novoStatusVenda })
      .then(() => {
        alert("Status de venda atualizado com sucesso!");
        atualizarDados();
      })
      .catch((error) => {
        alert("Erro ao atualizar status de venda.");
        console.error(error);
      });
  };

  return (
    <Container>
      <Header as="h1">Gerenciamento de Veículos</Header>

      <Button primary onClick={handleAbrirModal} style={{ marginBottom: "1rem" }}>
        Criar
      </Button>
      <Button primary onClick={carregarVeiculosCriadosUltimaSemana} style={{ marginBottom: "1rem" }}>
        Veículos Criados na Última Semana
      </Button>
      <Button primary onClick={carregarDistribuicaoPorDecada} style={{ marginBottom: "1rem" }}>
        Distribuição por Década
      </Button>
      <Button primary onClick={carregarVeiculosPorFabricante} style={{ marginBottom: "1rem" }}>
        Veículos por Fabricante
      </Button>
      <Button primary onClick={carregarVeiculosNaoVendidos} style={{ marginBottom: "1rem" }}>
        Veículos Não Vendidos
      </Button>

      <VeiculoForm
        open={modalAberto}
        onClose={handleFecharModal}
        onCreateVeiculo={handleVeiculoCriado}
        veiculoEditado={veiculoEditado}
        setVeiculoEditado={setVeiculoEditado}
      />

      <Form>
        <Form.Field>
          <label>Filtrar por Marca</label>
          <input
            placeholder="Marca"
            value={filtroMarca}
            onChange={(event) => setFiltroMarca(event.target.value)}
          />
        </Form.Field>
        <Form.Field>
          <label>Filtrar por Ano</label>
          <input
            placeholder="Ano"
            value={filtroAno}
            onChange={(event) => setFiltroAno(event.target.value)}
          />
        </Form.Field>
        <Form.Field>
          <label>Filtrar por Cor</label>
          <input
            placeholder="Cor"
            value={filtroCor}
            onChange={(event) => setFiltroCor(event.target.value)}
          />
        </Form.Field>
        <Button type="button" primary onClick={handleFiltrar}>
          Filtrar
        </Button>
      </Form>

      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>Veiculo</Table.HeaderCell>
            <Table.HeaderCell>Marca</Table.HeaderCell>
            <Table.HeaderCell>Ano</Table.HeaderCell>
            <Table.HeaderCell>Descrição</Table.HeaderCell>
            <Table.HeaderCell>Cor</Table.HeaderCell>
            <Table.HeaderCell>Vendido</Table.HeaderCell>
            <Table.HeaderCell>Ações</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {veiculos.map((veiculo) => (
            <Table.Row key={veiculo.id}>
              <Table.Cell>{veiculo.veiculo}</Table.Cell>
              <Table.Cell>{veiculo.marca}</Table.Cell>
              <Table.Cell>{veiculo.ano}</Table.Cell>
              <Table.Cell>{veiculo.descricao}</Table.Cell>
              <Table.Cell>{veiculo.cor}</Table.Cell>
              <Table.Cell>{veiculo.vendido ? "Sim" : "Não"}</Table.Cell>
              <Table.Cell>
                <Button primary onClick={() => handleEditarVeiculo(veiculo)}>
                  Editar
                </Button>
                <Button negative onClick={() => handleDeletarVeiculo(veiculo.id)}>
                  Excluir
                </Button>
                <Button
                  primary={veiculo.vendido}
                  onClick={() => handleVendaVeiculo(veiculo.id, veiculo.vendido)}
                >
                  {veiculo.vendido ? "Desmarcar como Vendido" : "Marcar como Vendido"}
                </Button>
              </Table.Cell>
            </Table.Row>
          ))}
        </Table.Body>
      </Table>

      <Header as="h2">Veículos Criados na Última Semana</Header>

      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>ID</Table.HeaderCell>
            <Table.HeaderCell>Veiculo</Table.HeaderCell>
            <Table.HeaderCell>Marca</Table.HeaderCell>
            <Table.HeaderCell>Ano</Table.HeaderCell>
            <Table.HeaderCell>Descrição</Table.HeaderCell>
            <Table.HeaderCell>Vendido</Table.HeaderCell>
            <Table.HeaderCell>Criado em</Table.HeaderCell>
            <Table.HeaderCell>Atualizado em</Table.HeaderCell>
            <Table.HeaderCell>Cor</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {veiculosCriadosUltimaSemana.map((veiculo) => (
            <Table.Row key={veiculo.id}>
              <Table.Cell>{veiculo.id}</Table.Cell>
              <Table.Cell>{veiculo.veiculo}</Table.Cell>
              <Table.Cell>{veiculo.marca}</Table.Cell>
              <Table.Cell>{veiculo.ano}</Table.Cell>
              <Table.Cell>{veiculo.descricao}</Table.Cell>
              <Table.Cell>{veiculo.vendido ? "Sim" : "Não"}</Table.Cell>
              <Table.Cell>{veiculo.created}</Table.Cell>
              <Table.Cell>{veiculo.updated}</Table.Cell>
              <Table.Cell>{veiculo.cor}</Table.Cell>
            </Table.Row>
          ))}
        </Table.Body>
      </Table>

      <Header as="h2">Distribuição por Década</Header>

      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>Década</Table.HeaderCell>
            <Table.HeaderCell>Quantidade</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {Object.entries(distribuicaoPorDecada).map(([decada, quantidade]) => (
            <Table.Row key={decada}>
              <Table.Cell>{decada}</Table.Cell>
              <Table.Cell>{quantidade}</Table.Cell>
            </Table.Row>
          ))}
        </Table.Body>
      </Table>

      <Header as="h2">Veículos por Fabricante</Header>

      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>Marca</Table.HeaderCell>
            <Table.HeaderCell>Quantidade</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {veiculosPorFabricante.map((veiculo) => (
            <Table.Row key={veiculo.marca}>
              <Table.Cell>{veiculo.marca}</Table.Cell>
              <Table.Cell>{veiculo.quantidade}</Table.Cell>
            </Table.Row>
          ))}
        </Table.Body>
      </Table>

      <Header as="h2">Veículos Não Vendidos</Header>

      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>ID</Table.HeaderCell>
            <Table.HeaderCell>Veiculo</Table.HeaderCell>
            <Table.HeaderCell>Marca</Table.HeaderCell>
            <Table.HeaderCell>Ano</Table.HeaderCell>
            <Table.HeaderCell>Descrição</Table.HeaderCell>
            <Table.HeaderCell>Vendido</Table.HeaderCell>
            <Table.HeaderCell>Criado em</Table.HeaderCell>
            <Table.HeaderCell>Atualizado em</Table.HeaderCell>
            <Table.HeaderCell>Cor</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {veiculosNaoVendidos.map((veiculo) => (
            <Table.Row key={veiculo.id}>
              <Table.Cell>{veiculo.id}</Table.Cell>
              <Table.Cell>{veiculo.veiculo}</Table.Cell>
              <Table.Cell>{veiculo.marca}</Table.Cell>
              <Table.Cell>{veiculo.ano}</Table.Cell>
              <Table.Cell>{veiculo.descricao}</Table.Cell>
              <Table.Cell>{veiculo.vendido ? "Sim" : "Não"}</Table.Cell>
              <Table.Cell>{veiculo.created}</Table.Cell>
              <Table.Cell>{veiculo.updated}</Table.Cell>
              <Table.Cell>{veiculo.cor}</Table.Cell>
            </Table.Row>
          ))}
        </Table.Body>
      </Table>
    </Container>
  );
};

export default App;
