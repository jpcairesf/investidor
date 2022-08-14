# Investidor API

API REST em Java com Spring e testes com JUnit + Mockito.

Realiza cadastro e consulta de movimentações:
  - Aplicações - Data, CNPJ, Valor, Ativo
  - Resgates - Data, CNPJ, Valor, Ativo
  - Valida o CNPJ do investidor
  - Não é possível resgatar valores maiores que o disponível em carteira de investimentos
  - Não há validação para aplicação (compra) de ativos, visto que seria papel de uma outra API que gerencia carteira e saldo
  - As movimentações só são realizadas em Ativos válidos

Consulta extrato de carteira de investimentos:
  - Aplica filtros de CNPJ, data de referência e período
  - Exibe dados do investidor e das movimentações dentro do período informado a partir da data de referência
  - Gera relatório com o volume financeiro aplicado por investidor ordenados de forma decrescente

# Ajustes e melhorias

O projeto ainda está em desenvolvimento e as próximas atualizações serão voltadas nas seguintes tarefas:

- [x] Testes na camada de serviço
- [ ] Testes de integração
- [ ] Autenticação e login

# ☕ Utilização

O deploy da aplicação foi feito no Heroku. Acesse https://investidor-api.herokuapp.com/ para utilizar.

## /ativo

**Request Methods**
```
GET /
return list of ativo

GET /{id}
return ativo

POST{RequestBody} /
return ativo

PUT{RequestBody} /{id}
return ativo

DELETE /{id}
return void
```
**Request Body** - Exemplo em JSON
```
{
  'nome':'exemplo'
}
```
**Response** - Exemplo de ativo em JSON
```
{
  'id':1,
  'nome':'exemplo'
}
```

## /investidor

**Request Methods**
```
GET /
return list of investidor

GET /{id}
return investidor

POST{RequestBody} /
return investidor

PUT{RequestBody} /{id}
return investidor

DELETE /{id}
return void
```
**Request Body** - Exemplo em JSON
```
{
  'nome':'exemplo',
  'cnpj':'00.000.000/0001-00'
}
```
**Response** - Exemplo de investidor em JSON
```
{
  'id':1,
  'nome':'exemplo',
  'cnpj':'00.000.000/0001-00'
}
```

## /movimentacao

**Request Methods**
```
GET /
return list of movimentacao

GET /buscar?investidor={cnpj}
      &ativo={nome}
      &tipo={tipoDeMovimentacao}
      &data-inicio={yyyy-MM-dd}
      &data-fim={yyyy-MM-dd}
return list of movimentacao

POST{RequestBody} /aplicar
return movimentacao

POST{RequestBody} /resgatar
return movimentacao
```
**Request Body** - Exemplo de movimentacao em JSON
```
{
  'cnpj':'00.000.000/0001-00',
  'ativo':'nome',
  'quantidade':1
}
```
**Response** - Exemplo em JSON
```
{
  'id':1,
  'ativoId':1,
  'investidorId':1
  'tipo':'tipoDeMovimentacao',
  'data':'2022-01-01'
  'quantidade':1
}
```

## /extrato

**Request Methods**
```
GET ?investidor={cnpj}
      &ativo={nome} (not required)
      &tipo={tipoDeMovimentacao} (not required)
      &data-inicio={yyyy-MM-dd}
      &periodo={integer} (not required, default=30)
return extrato
```
**Response** - Exemplo de extrato em JSON
```
{
  'nome':'nome',
  'cnpj':'00.000.000/0001-00',
  'movimentacoes':[
    {
      'ativo':'nome1',
      'tipo':'tipoMovimentacao',
      'quantidade':1,
      'data':'2022-01-01'
    },
    {
      'ativo':'nome2',
      'tipo':'tipoMovimentacao',
      'quantidade':2,
      'data':'2022-02-02'
    }
  ]
}
```

## /volume-aplicado

**Request Methods**
```
GET /
return volume aplicado
```
**Response** - Exemplo de volume aplicado em JSON
```
[
  {
    'nome':'nome1',
    'cnpj':'10.000.000/0001-00',
    'volume':10
  },
  {
    'nome':'nome2',
    'cnpj':'20.000.000/0001-00'
    'volume':2,
  }
]
```
