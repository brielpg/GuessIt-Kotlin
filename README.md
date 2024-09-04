# Desafio: GuessIt!

## Objetivo

Desenvolver uma aplicação Android onde o jogador deve adivinhar um número aleatório escolhido pela aplicação.
O número será gerado aleatoriamente no intervalo de 1 a 25, e o usuário será solicitado a inserir o seu palpite.
Este será o nosso **número misterioso**! :see_no_evil:

## Instruções

- A aplicação deve gerar um número aleatório entre 1 e 25.
- Um campo de entrada será usado para o usuário inserir o número.
- O app deve conter dois botões:
  - Um botão "Adivinhar" para validar a tentativa.
  - Um botão "Jogar novamente" para reiniciar o jogo.
- O aplicativo deverá exibir dicas sobre o palpite do jogado.
- O usuário deverá acertar o número misterioso em no máximo 10 tentativas.
- Caso seja enviado um valor inválido, uma mensagem de erro será exibida.

## Requisitos

- O layout deve conter:
  - Um elemento com o `id` configurado para `mainTextView`
    - Exibir a mensagem **Adivinhe o número entre 1 e 25** no início do jogo
    - Exibir a mensagem **Muito alto!** quando o palpite for maior que o número misterioso
    - Exibir a mensagem **Muito baixo!** quando o palpite for menor que o número misterioso
    - Exibir a mensagem **Parabéns! Você acertou.** quando o palpite for o número misterioso
    - Exibir a mensagem **Que pena, você não acertou o número 1!** quando o jogador errar todas as tentativas
  - Um elemento com id `guessNumberEditText` para o jogador inserir o palpite
  - Um botão com o `id` configurado para `guessButton` com o texto **Adivinhar**
    - Quando o jogo terminar, deverá ser desabilitado
  - Um botão com o `id` configurado para `restartButton` com o texto **Jogar novamente**
    - Só deverá ser exibido quando o jogo terminar
  - Um elemento com o `id` configurado para `remainingAttemptsTextView` exibindo as tentativas restantes
    - Exibir apenas depois da primeira tentativa
    - Exibir a quantidade de tentativas restantes no formato `Tentativas restantes: 9`
    - Na última tentativa, exibir o texto: `Última tentativa!`
  - Um `TextView` invisível para exibir mensagens de erro (`errorMessageTextView`).
  - Um elemento com o `id` configurado para `errorMessageTextView` para exibir os erros
    - Exibir somente quando ocorrer o erro
    - Exibir uma mensagem genérica: `Insira um valor válido!`
- O número gerado deve ser aleatório sempre que o jogo for iniciado.
- As mensagens devem ser exibidas após cada tentativa.
- Após 10 tentativas erradas, o jogo deve exibir uma mensagem e permitir que o jogador reinicie o jogo.
- Faça commits regulares no projeto conforme avança no desenvolvimento.

## Testes

O projeto possui testes unitários que podem ser utilizados para te ajudar a concluir o desafio!

---

Boa sorte e divirta-se desenvolvendo! :shipit: