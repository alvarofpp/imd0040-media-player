# IMD0040 - Linguagem de Programação II - T01 (2018.1)

Trabalho final da disciplina - unidade 3.

**Componentes**:
- <a href="https://github.com/alvarofpp">Álvaro Ferreira Pires de Paiva</a>
  - Matrícula: 2016039162
  - E-mail: alvarofepipa@gmail.com
- <a href="https://github.com/israelfontes">Israel Medeiros Fontes</a>
  - Matrícula: 2016037140 
  - E-mail: israeljapi@gmail.com

## Git

Git é um sistema de controle de versão distribuído e um sistema de gerenciamento de código fonte e arquivos no geral.

### Comandos básicos do Git

- `git status` - Mostra os arquivos alterados no seu diretório
- `git add .` - Adiciona todos os arquivos ao próximo commit (substitua o '.' pelo caminho de um arquivo para adicionar de um em um)
- `git commit 'texto'` - Aplica o commit aos arquivos anteriormente adicionados
- `git push` - Publica as alterações locais na branch remota
- `git branch` - Lista as branchs existentes localmente
- `git checkout [nome da branch]` - Muda de branch
- `git pull` - Baixa as alterações salvas na branch remota

### Passo-a-passo

Digamos que você está utilizando a branch *master*.

- `git checkout -b [nome da nova branch]` - Esse comando já cria uma nova branch e coloca você nela
- `git add .` - Adiciona os arquivos que você alterou
- `git commit 'texto'` - Aplica o commit aos arquivos anteriormente adicionados
- `git push` - Salva remotamente as alterações que você fez

Quando você cria uma branch local, ela não existe no repositório remoto, então você deve especificar isso no seu primeiro **push** usando o comando: `git push -u origin [nome da branch]`.

Quando você tiver feito o Merge Request (também conhecido como Pull Request), apague a sua branch local (`git branch -D [nome da branch]`) e crie uma nova.

Observações:
- **JAMAIS** salve alterações na branch **master**. Isso pode acarretar em conflitos severos nos arquivos dos seus coleguinhas, atrapalhando muito o desenvolvimento do projeto.

### Orientações gerais

- [Sobre o Git](http://www.letmegooglethat.com/?q=git)