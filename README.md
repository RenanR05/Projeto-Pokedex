# Pokédex Multiplatform (KMP)

Este é um projeto de estudo desenvolvido em **Kotlin Multiplatform (KMP)** e **Compose Multiplatform** para a disciplina de Programação para Dispositivos Móveis II na UNIVALI.

## Integrantes da Dupla
- Renan Regis
- Luan Regis
> [!IMPORTANT]
> Favor editar o arquivo README.md para incluir os nomes dos integrantes da dupla.

## Objetivo
Desenvolver uma aplicação Pokédex compartilhada entre Android e iOS, explorando conceitos de navegação tipada, gerenciamento de estado compartilhado e diferenciação de UI via `expect/actual`.

## Funcionalidades
- **Home Screen**: Dashboard com identidade visual impactante.
- **Pokedex List**: Listagem em Grid com busca (SearchBar).
- **Pokemon Details**: Detalhes completos, descrição e estatísticas animadas.
- **Team Builder**: Gerenciamento de time (máximo 6 Pokémons).
- **Expect/Actual**: Implementação diferenciada da tela de Time para Android (Material 3) e iOS (Apple Human Interface).

## Requisitos
- **Android Studio** (Koala ou superior recomendado)
- **Kotlin Multiplatform SDK**
- **Xcode** (necessário para rodar no iOS)
- **macOS** (necessário para compilar o alvo iOS)

## Como Executar
1. Clone o repositório.
2. Abra o projeto no **Android Studio**.
3. Aguarde o Gradle sincronizar as dependências.
4. Selecione `composeApp` e execute no emulador Android.
5. Para iOS, use o Xcode ou o próprio Android Studio (com o plugin KMP) para rodar no simulador.

## Tecnologias Utilizadas
- **Kotlin Multiplatform**: Lógica de negócios compartilhada.
- **Compose Multiplatform**: UI compartilhada.
- **Jetpack Navigation**: Navegação tipada com `@Serializable`.
- **Coil 3**: Carregamento de imagens (sprites) via rede.
- **Material 3**: Design System base da aplicação.
- **Expect/Actual**: Mecanismo para componentes específicos de plataforma.
