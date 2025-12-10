 # MapRecycling

Aplicativo Android desenvolvido com **Jetpack Compose** que promove a **reciclagem sustentável**, permitindo que usuários encontrem pontos de coleta próximos, realizem login/cadastro, gerenciem seu perfil e tenham acesso a informações ambientais de forma simples e acessível.

---

 Funcionalidades

✅ Autenticação de usuários (Login / Cadastro)  
✅ Exibição de pontos de reciclagem em mapa interativo  
✅ Popup informativo ao selecionar um ponto no mapa  
✅ Tela de perfil com dados do usuário  
✅ Edição e persistência de dados no Firebase  
✅ Navegação fluida entre telas  
✅ Design sustentável e acessível   

---

 Arquitetura

O projeto segue a **arquitetura MVVM (Model–View–ViewModel)**, garantindo:

- Separação de responsabilidades  
- Código mais organizado e testável  
- Facilidade de manutenção e expansão  


---

##  Gerenciamento de Estado

O aplicativo utiliza:

- **StateFlow** → estados da tela  
- **Channel / Flow** → eventos únicos (one-shot events)
  - Navegação
  - Mensagens de erro
  - Feedback de sucesso

📌 Exemplo de eventos:
- `NavigateHome`
- `ShowError`

---

##  Tecnologias Utilizadas

###  UI
- Jetpack Compose
- Material 3
- Layout responsivo para diferentes tamanhos de tela

###  Arquitetura
- MVVM
- ViewModel
- StateFlow
- Coroutines (`viewModelScope`)

###  Backend
- Firebase Authentication
- Firebase Firestore

### Mapas
- MapLibre
- OpenStreetMap
- GeoJSON

---

##  Navegação

O app utiliza o **Navigation Component para Compose**, garantindo:

- Navegação controlada por rotas
- Limpeza da pilha de telas ao login/logout
- Navegação segura entre telas

Rotas principais:
- `login`
- `register`
- `home`
- `map`
- `profile`

---

##  Sustentabilidade no Design

O design do app foi pensado com foco sustentável:

- Paleta de cores verdes   
- Ícones ambientais  
- Interface limpa e intuitiva  
- Feedback claro para o usuário  

---

##  Acessibilidade

O projeto aplica boas práticas de acessibilidade:

- Labels descritivos para leitores de tela  
- Contraste adequado de cores  
- Componentes grandes e espaçados  
- Navegação simples por toque/gestos  

---

##  Operações Assíncronas

Utiliza **Coroutines** para:

- Login e cadastro
- Leitura e escrita no Firestore
- Controle de loading e erros

Exemplo:
```kotlin
viewModelScope.launch {
    _uiState.update { it.copy(loading = true) }
}


