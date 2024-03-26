# Desafio: Cálculo de Preço de Frete

Descrição:
Você faz parte de uma empresa de logística, responsável por um novo projeto voltado para calcular o preço do frete com base no peso dos produtos. Seu objetivo é desenvolver um endpoint POST em uma API que receba os detalhes dos produtos a serem enviados e calcule o preço final do frete com base nessas informações.

Estrutura do Objeto JSON:
O endpoint receberá um objeto JSON contendo detalhes dos produtos a serem enviados, incluindo o peso de cada produto. Além disso, você terá acesso a um banco de dados com a tabela de preços de frete, onde poderá buscar as informações necessárias para realizar o cálculo.

# Stack
O contribuidor pode fazer em qualquer lang e utilizar qualquer ferramenta que achar necessario para alcancer a melhor performance.

# Regras:
O CLIENTE precisa ter um cnpj valido.

O cep precisa estar no range da tabela de preço de frete.

O peso não pode ser superior a 10 ton

# Dados
Exemplo de requisição:

{
"empresa": "Obramax",
"cnpj": "00000000000000",
"endereco": {
"rua": "rua dos bobos",
"numero": "10",
"cep": "0000000"
},
"itens": [
{
"nome": "Item 1",
"quantidade": 2,
"peso_produto": 10,
"medida_peso": "kg"
},
{
"nome": "Item 2",
"quantidade": 2,
"preco_unitario": 20.00,
"peso_produto": 500,
"medida_peso": "gr"
},
{
"nome": "Item 2",
"quantidade": 1,
"preco_unitario": 20.00,
"peso_produto": 1,
"medida_peso": "ton"
}
]
}

Exemplo de retorno:

{
"mensagem": "processado com sucesso",
"preco_final_frete": 100,
"peso_total_kg": 10
}

SQL:
CREATE TABLE tabela_frete (
id INT AUTO_INCREMENT PRIMARY KEY,
faixa_peso VARCHAR(100),
faixa_cep_inicio VARCHAR(10),
faixa_cep_fim VARCHAR(10),
tarifa DECIMAL(10, 2)
);

INSERT INTO tabela_frete (faixa_peso, faixa_cep_inicio, faixa_cep_fim, tarifa)
VALUES
('0 a 10kg', '10000', '20000', 15.00),
('10 a 100kg', '10000', '20000', 25.00),
('100kg a 1ton', '20001', '50000', 100.00),
('1ton a 10ton', '20001', '50000', 500.00);

