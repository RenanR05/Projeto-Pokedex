# Pokédex Multiplatform (KMP)

Este é um projeto de estudo desenvolvido em **Kotlin Multiplatform (KMP)** e **Compose Multiplatform** para a disciplina de Programação para Dispositivos Móveis II na UNIVALI.

## Integrantes da Dupla
- Renan Regis
- Luan Regis

## Objetivo
Desenvolver uma aplicação Pokédex compartilhada entre Android e iOS, explorando conceitos de navegação tipada, gerenciamento de estado compartilhado e diferenciação de UI via `expect/actual`.

## Funcionalidades
- **Home Screen**: Dashboard com identidade visual impactante.
- **Pokedex List**: Listagem em Grid com busca (SearchBar) e filtro por tipo.
- **Pokemon Details**: Detalhes completos, descrição e estatísticas animadas.
- **Team Builder**: Gerenciamento de time com foto de captura e coordenadas GPS.
- **Câmera Nativa**: Ao capturar um Pokémon, o app abre a câmera para tirar a foto do local.
- **Geolocalização GPS**: Coordenadas de latitude e longitude são salvas automaticamente no momento da captura.
- **Persistência Local (Room)**: Banco de dados com migração automática (v1 → v2) adicionando os campos `latitude`, `longitude` e `photoPath`.
- **Expect/Actual**: Implementação diferenciada da tela de Time para Android (Material 3) e iOS (Human Interface Guidelines).

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
