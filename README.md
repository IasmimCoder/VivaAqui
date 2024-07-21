# VivaAqui
VivaAqui é uma aplicação de gerenciamento de imóveis desenvolvida com Spring Boot. Permite listar, buscar e gerenciar imóveis disponíveis para venda ou aluguel e verificar propriedades próximas com base em coordenadas geográficas A aplicação combina PostgreSQL para armazenamento persistente de dados relacionais e Redis para operações geoespaciais eficientes.
## Funcionalidades
1. CRUD de propriedades: Permite adicionar imóveis com coordenadas geográficas.
2. Buscar imóveis dentro de um raio específico: Permite encontrar imóveis próximos a uma localização especificada pelo usuário.

## Tecnologias Utilizadas
Java 17+
Spring Boot
Spring Data JPA (Hibernate)
PostgreSQL
Redis
Maven
Docker

## Estados da Propriedade
OCUPADO
DISPONÍVEL PARA ALUGUEL
DISPONÍVEL PARA VENDA

## Combinação de PostgreSQL e Redis
Ao combinar PostgreSQL e Redis, você pode aproveitar o melhor dos dois mundos:
- PostgreSQL: Use PostgreSQL para armazenar dados relacionais, estruturados e transacionais, onde a integridade e a complexidade das consultas são importantes.
- Redis: Use Redis para operações de leitura e escrita rápidas, cache de dados frequentemente acessados e operações geoespaciais eficientes.

## Exemplo de Uso na Aplicação de Imóveis
Na sua aplicação de gerenciamento de imóveis:
1. PostgreSQL: Pode ser usado para armazenar detalhes dos imóveis, informações de usuários, contratos, históricos de transações, etc.
2. Redis: Pode ser usado para armazenar as coordenadas geográficas dos imóveis e realizar operações de busca de proximidade de forma eficiente. Por exemplo, quando um usuário busca imóveis próximos a uma localização específica, você pode usar Redis para obter rapidamente os IDs dos imóveis dentro do raio desejado e, em seguida, buscar os detalhes desses imóveis no PostgreSQL.

