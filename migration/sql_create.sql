CREATE TABLE fc_forma_pagamento (
  id SERIAL  NOT NULL ,
  nome VARCHAR(100)   NOT NULL ,
  ativo BOOL   NOT NULL ,
  acrescimo DOUBLE  DEFAULT 0 NOT NULL   ,
PRIMARY KEY(id));




CREATE TABLE fc_conta_categoria (
  id SERIAL  NOT NULL ,
  nome VARCHAR(100)   NOT NULL ,
  ativo BOOL   NOT NULL ,
  tipo CHAR(1)   NOT NULL   ,
PRIMARY KEY(id));





CREATE TABLE fc_contas_bancaria (
  id INTEGER   NOT NULL ,
  nome VARCHAR(100)   NOT NULL ,
  tipo CHAR(1)   NOT NULL ,
  agencia INTEGER    ,
  conta_corrente INTEGER    ,
  carteira INTEGER    ,
  modalidade INTEGER      ,
PRIMARY KEY(id));





CREATE TABLE sh_cidade (
  cep SERIAL  NOT NULL ,
  nome VARCHAR(100)   NOT NULL ,
  uf CHAR(2)   NOT NULL   ,
PRIMARY KEY(cep));




CREATE TABLE sh_veiculo_marca (
  id SERIAL  NOT NULL ,
  nome VARCHAR(100)   NOT NULL   ,
PRIMARY KEY(id));




CREATE TABLE sh_ordem_tipo_servico (
  id INTEGER   NOT NULL ,
  nome VARCHAR(100)   NOT NULL ,
  ativo BOOL   NOT NULL ,
  valor_entrada DECIMAL(10,2)   NOT NULL ,
  valor_saida DECIMAL(10,2)   NOT NULL   ,
PRIMARY KEY(id));




CREATE TABLE sh_ordem_status (
  id SERIAL  NOT NULL ,
  nome VARCHAR(100)   NOT NULL ,
  ativo BOOL   NOT NULL ,
  finaliza BOOL   NOT NULL   ,
PRIMARY KEY(id));




CREATE TABLE es_prateleira (
  id INTEGER   NOT NULL ,
  descricao VARCHAR(100)   NOT NULL   ,
PRIMARY KEY(id));




CREATE TABLE es_item_tipo (
  id INTEGER   NOT NULL ,
  nome VARCHAR(100)   NOT NULL ,
  ativo BOOL   NOT NULL   ,
PRIMARY KEY(id));




CREATE TABLE sh_pessoa (
  id SERIAL  NOT NULL ,
  cep INTEGER   NOT NULL ,
  nome VARCHAR(100)   NOT NULL ,
  tipo CHAR(1)   NOT NULL ,
  nascimento DATE   NOT NULL ,
  telefone VARCHAR(20)   NOT NULL ,
  telefone_secundario VARCHAR(20)    ,
  email VARCHAR(100)    ,
  endereco VARCHAR(100)      ,
PRIMARY KEY(id)  ,
  FOREIGN KEY(cep)
    REFERENCES sh_cidade(cep));


CREATE INDEX sh_pessoa_FKIndex1 ON sh_pessoa (cep);



CREATE INDEX IFK_fk_pessoa_cidade ON sh_pessoa (cep);


CREATE TABLE sh_veiculo_modelo (
  id SERIAL  NOT NULL ,
  id_marca INTEGER   NOT NULL ,
  nome VARCHAR(100)      ,
PRIMARY KEY(id)  ,
  FOREIGN KEY(id_marca)
    REFERENCES sh_veiculo_marca(id));


CREATE INDEX sh_veiculo_modelo_FKIndex1 ON sh_veiculo_modelo (id_marca);


CREATE INDEX IFK_Rel_08 ON sh_veiculo_modelo (id_marca);


CREATE TABLE sh_usuario (
  id_pessoa INTEGER   NOT NULL ,
  usuario VARCHAR(100)   NOT NULL ,
  senha VARCHAR(100)   NOT NULL ,
  nivel CHAR(1)   NOT NULL ,
  ativo BOOL   NOT NULL   ,
PRIMARY KEY(id_pessoa)  ,
  FOREIGN KEY(id_pessoa)
    REFERENCES sh_pessoa(id));


CREATE INDEX sh_usuario_FKIndex1 ON sh_usuario (id_pessoa);



CREATE INDEX IFK_Rel_05 ON sh_usuario (id_pessoa);


CREATE TABLE es_item (
  id SERIAL  NOT NULL ,
  id_prateleira INTEGER   NOT NULL ,
  id_peca_tipo INTEGER   NOT NULL ,
  nome VARCHAR(100)      ,
PRIMARY KEY(id)    ,
  FOREIGN KEY(id_peca_tipo)
    REFERENCES es_item_tipo(id),
  FOREIGN KEY(id_prateleira)
    REFERENCES es_prateleira(id));


CREATE INDEX es_pecas_FKIndex1 ON es_item (id_peca_tipo);
CREATE INDEX es_pecas_FKIndex2 ON es_item (id_prateleira);


CREATE INDEX IFK_Rel_19 ON es_item (id_peca_tipo);
CREATE INDEX IFK_Rel_23 ON es_item (id_prateleira);


CREATE TABLE fc_conta (
  id INTEGER   NOT NULL ,
  id_pessoa INTEGER   NOT NULL ,
  id_forma_pagamento INTEGER   NOT NULL ,
  id_conta_categoria INTEGER   NOT NULL ,
  nota_fiscal VARCHAR(30)    ,
  descricao VARCHAR(200)      ,
PRIMARY KEY(id)      ,
  FOREIGN KEY(id_conta_categoria)
    REFERENCES fc_conta_categoria(id),
  FOREIGN KEY(id_forma_pagamento)
    REFERENCES fc_forma_pagamento(id),
  FOREIGN KEY(id_pessoa)
    REFERENCES sh_pessoa(id));


CREATE INDEX fc_contas_FKIndex1 ON fc_conta (id_conta_categoria);
CREATE INDEX fc_contas_FKIndex2 ON fc_conta (id_forma_pagamento);
CREATE INDEX fc_contas_FKIndex3 ON fc_conta (id_pessoa);


CREATE INDEX IFK_Rel_13 ON fc_conta (id_conta_categoria);
CREATE INDEX IFK_Rel_14 ON fc_conta (id_forma_pagamento);
CREATE INDEX IFK_Rel_15 ON fc_conta (id_pessoa);


CREATE TABLE sh_pessoa_juridica (
  id_pessoa INTEGER   NOT NULL ,
  cnpj CHAR(14)   NOT NULL ,
  nome_fantasia VARCHAR(100)    ,
  razao_social VARCHAR(100)      ,
PRIMARY KEY(id_pessoa)  ,
  FOREIGN KEY(id_pessoa)
    REFERENCES sh_pessoa(id));


CREATE INDEX sh_pessoa_juridica_FKIndex1 ON sh_pessoa_juridica (id_pessoa);


CREATE INDEX IFK_Rel_04 ON sh_pessoa_juridica (id_pessoa);


CREATE TABLE sh_pessoa_fisica (
  id_pessoa INTEGER   NOT NULL ,
  sexo CHAR(1)   NOT NULL ,
  cpf CHAR(11)   NOT NULL   ,
PRIMARY KEY(id_pessoa)  ,
  FOREIGN KEY(id_pessoa)
    REFERENCES sh_pessoa(id));


CREATE INDEX sh_pessoa_fisica_FKIndex1 ON sh_pessoa_fisica (id_pessoa);


CREATE INDEX IFK_Rel_03 ON sh_pessoa_fisica (id_pessoa);


CREATE TABLE sh_veiculo (
  placa SERIAL  NOT NULL ,
  id_modelo INTEGER   NOT NULL ,
  cor VARCHAR(50)   NOT NULL ,
  ano INTEGER    ,
  chassi INTEGER      ,
PRIMARY KEY(placa)  ,
  FOREIGN KEY(id_modelo)
    REFERENCES sh_veiculo_modelo(id));


CREATE INDEX sh_veiculo_FKIndex1 ON sh_veiculo (id_modelo);


CREATE INDEX IFK_Rel_09 ON sh_veiculo (id_modelo);


CREATE TABLE fc_contas_parcelas (
  id INTEGER   NOT NULL ,
  id_conta INTEGER   NOT NULL ,
  id_conta_bancaria INTEGER    ,
  parcela INTEGER   NOT NULL ,
  data_lancamento DATE   NOT NULL ,
  valor DECIMAL(10,2)   NOT NULL ,
  situacao CHAR(1)   NOT NULL ,
  data_pagamento DATE    ,
  boleto VARCHAR(100)    ,
  valor_pagamento DECIMAL(10,2)      ,
PRIMARY KEY(id)    ,
  FOREIGN KEY(id_conta_bancaria)
    REFERENCES fc_contas_bancaria(id),
  FOREIGN KEY(id_conta)
    REFERENCES fc_conta(id));


CREATE INDEX fc_contas_parcelas_FKIndex1 ON fc_contas_parcelas (id_conta_bancaria);
CREATE INDEX fc_contas_parcelas_FKIndex2 ON fc_contas_parcelas (id_conta);


CREATE INDEX IFK_Rel_16 ON fc_contas_parcelas (id_conta_bancaria);
CREATE INDEX IFK_Rel_18 ON fc_contas_parcelas (id_conta);


CREATE TABLE est_estoque_entrada (
  id INTEGER   NOT NULL ,
  id_conta INTEGER   NOT NULL ,
  id_pessoa INTEGER   NOT NULL ,
  descricao VARCHAR(100)    ,
  nota_fiscal VARCHAR(100)    ,
  data_compra DATE   NOT NULL ,
  valor DECIMAL(10,2)   NOT NULL ,
  tipo CHAR(1)   NOT NULL   ,
PRIMARY KEY(id)    ,
  FOREIGN KEY(id_pessoa)
    REFERENCES sh_pessoa(id),
  FOREIGN KEY(id_conta)
    REFERENCES fc_conta(id));


CREATE INDEX est_compra_FKIndex1 ON est_estoque_entrada (id_pessoa);
CREATE INDEX est_compra_FKIndex2 ON est_estoque_entrada (id_conta);


CREATE INDEX IFK_Rel_24 ON est_estoque_entrada (id_pessoa);
CREATE INDEX IFK_Rel_25 ON est_estoque_entrada (id_conta);


CREATE TABLE sh_ordem (
  id SERIAL  NOT NULL ,
  id_pessoa INTEGER   NOT NULL ,
  placa VARCHAR(7)    ,
  id_status INTEGER   NOT NULL ,
  prazo DATE    ,
  descricao VARCHAR(100)    ,
  valor DECIMAL(10,2)    ,
  km INTEGER    ,
  desconto DECIMAL(10,2)      ,
PRIMARY KEY(id)      ,
  FOREIGN KEY(id_status)
    REFERENCES sh_ordem_status(id),
  FOREIGN KEY(placa)
    REFERENCES sh_veiculo(placa),
  FOREIGN KEY(id_pessoa)
    REFERENCES sh_pessoa(id));


CREATE INDEX sh_ordem_servico_FKIndex1 ON sh_ordem (id_status);
CREATE INDEX sh_ordem_FKIndex2 ON sh_ordem (placa);
CREATE INDEX sh_ordem_FKIndex3 ON sh_ordem (id_pessoa);


CREATE INDEX IFK_Rel_06 ON sh_ordem (id_status);
CREATE INDEX IFK_Rel_12 ON sh_ordem (placa);
CREATE INDEX IFK_Rel_13 ON sh_ordem (id_pessoa);


CREATE TABLE est_estoque (
  id INTEGER   NOT NULL ,
  id_entrada INTEGER   NOT NULL ,
  lote INTEGER    ,
  id_peca INTEGER   NOT NULL ,
  quantidade_compra INTEGER   NOT NULL ,
  quantidade_disponivel INTEGER   NOT NULL ,
  valor_unidade DECIMAL(10,2)   NOT NULL ,
  valor_unidade_venda DECIMAL(10,2)   NOT NULL ,
  data_validade DATE      ,
PRIMARY KEY(id)    ,
  FOREIGN KEY(id_peca)
    REFERENCES es_item(id),
  FOREIGN KEY(id_entrada)
    REFERENCES est_estoque_entrada(id));


CREATE INDEX est_estoque_FKIndex1 ON est_estoque (id_peca);
CREATE INDEX est_estoque_FKIndex2 ON est_estoque (id_entrada);


CREATE INDEX IFK_Rel_14 ON est_estoque (id_peca);
CREATE INDEX IFK_Rel_22 ON est_estoque (id_entrada);


CREATE TABLE sh_ordem_servico (
  id_tipo_servico INTEGER   NOT NULL ,
  id_ordem INTEGER   NOT NULL ,
  valorentrada DECIMAL(10,2)   NOT NULL ,
  valorsaida DECIMAL(10,2)   NOT NULL ,
  quantidade INTEGER   NOT NULL ,
  data_realizado DATE   NOT NULL ,
  desconto DECIMAL(10,2)      ,
PRIMARY KEY(id_tipo_servico, id_ordem)    ,
  FOREIGN KEY(id_tipo_servico)
    REFERENCES sh_ordem_tipo_servico(id),
  FOREIGN KEY(id_ordem)
    REFERENCES sh_ordem(id));


CREATE INDEX sh_ordem_tipo_servico_has_sh_ordem_servico_FKIndex1 ON sh_ordem_servico (id_tipo_servico);
CREATE INDEX sh_ordem_tipo_servico_has_sh_ordem_servico_FKIndex2 ON sh_ordem_servico (id_ordem);


CREATE INDEX IFK_Rel_06 ON sh_ordem_servico (id_tipo_servico);
CREATE INDEX IFK_Rel_07 ON sh_ordem_servico (id_ordem);


CREATE TABLE es_estoque_movimentacao (
  id INTEGER   NOT NULL ,
  id_estoque INTEGER   NOT NULL ,
  valor_venda DECIMAL(10,2)   NOT NULL ,
  data_lancamento DATE   NOT NULL ,
  descricao VARCHAR(100)   NOT NULL ,
  situacao CHAR(1)   NOT NULL ,
  quantidade INTEGER   NOT NULL ,
  valor_venda_unitario DECIMAL(10,2)    ,
  desconto DECIMAL(10,2)      ,
PRIMARY KEY(id)  ,
  FOREIGN KEY(id_estoque)
    REFERENCES est_estoque(id));


CREATE INDEX es_estoque_movimentacao_FKIndex1 ON es_estoque_movimentacao (id_estoque);


CREATE INDEX IFK_Rel_20 ON es_estoque_movimentacao (id_estoque);


CREATE TABLE sh_ordem_pecas (
  id_ordem INTEGER   NOT NULL ,
  id_estoque_movimentacao INTEGER   NOT NULL   ,
PRIMARY KEY(id_ordem, id_estoque_movimentacao)    ,
  FOREIGN KEY(id_ordem)
    REFERENCES sh_ordem(id),
  FOREIGN KEY(id_estoque_movimentacao)
    REFERENCES es_estoque_movimentacao(id));


CREATE INDEX sh_ordem_has_es_estoque_movimentacao_FKIndex1 ON sh_ordem_pecas (id_ordem);
CREATE INDEX sh_ordem_has_es_estoque_movimentacao_FKIndex2 ON sh_ordem_pecas (id_estoque_movimentacao);


CREATE INDEX IFK_Rel_21 ON sh_ordem_pecas (id_ordem);
CREATE INDEX IFK_Rel_22 ON sh_ordem_pecas (id_estoque_movimentacao);



