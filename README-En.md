<p align="center">
  <img src="docs/logo.png" alt="State Machine Template Factory Logo" width="120" />
</p>

<h1 align="center">State Machine Template Factory</h1>

<p align="center">
  Visual State Machine Designer · Drag-and-Drop State Diagrams · Export Java Code Templates
</p>

<p align="center">
  English | <a href="README.md">中文</a>
</p>

<p align="center">
  <a href="https://github.com/boommanpro/state-machine-template-factory/releases"><img alt="GitHub Release" src="https://img.shields.io/github/v/release/boommanpro/state-machine-template-factory?style=flat-square"></a>
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/license-MIT-blue.svg?style=flat-square"></a>
  <a href="https://github.com/boommanpro/state-machine-template-factory/actions"><img alt="Build Status" src="https://img.shields.io/github/actions/workflow/status/boommanpro/state-machine-template-factory/deploy.yml?branch=main&style=flat-square"></a>
  <img alt="Java" src="https://img.shields.io/badge/Java-1.8-orange.svg?style=flat-square">
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-2.7.14-green.svg?style=flat-square">
  <img alt="Vue 3" src="https://img.shields.io/badge/Vue-3-42b883.svg?style=flat-square">
</p>

---

## Introduction

`State Machine Template Factory` is a visual state machine designer that solves the pain of maintaining state transitions in business systems — chaotic flows, magic values, and high communication costs between product and engineering teams.

In business development, state changes are commonly implemented via ① branching ② lookup tables ③ state machines. When business logic gets complex, state transitions become obscure and states are set arbitrarily. This project provides a visual solution: **after product-engineering alignment, a flow diagram is produced, which can be exported as Java code templates, eliminating magic values in code**.

### What It Solves

Abstractly, a state change involves:

1. **State** — current state, target state
2. **Event** — triggers the transition from current to target state
3. **Condition** — transition guard
4. **Action** — transition side effect

The desired engineering form:

- After product-engineering alignment, output a flow diagram describing what state data is stored and which actions trigger A→B
- The flow diagram can be converted to Java code and enums, avoiding magic values
- Which states can change should be dynamic — driven by a config center or config file, i.e. creating state machines from configuration instead of hard-coding
- State machine changes support PlantUML export for collaboration with LLMs
- State transitions are recordable and quickly viewable on the frontend
- Enhancement: support key-based multi-version control, so historical flows are completed against historical state machines

## Screenshot

![State Machine Template Factory Screenshot](docs/screenshot.png)

## Live Demo

Hosted on GitHub Pages: <https://boommanpro.github.io/state-machine-template-factory/>

## Key Features

- **Visual Drag-and-Drop Design** — powered by [LogicFlow](https://site.logic-flow.cn/)
- **Multiple Node Types** — built-in start, end, task, push, user, time, click, download nodes
- **State & Event Management** — visually manage states and events
- **JSON Configuration Driven** — state machines configured via JSON, no hard-coding
- **Java Code Template Export** — generate Java templates based on `package-name` and other parameters
- **PlantUML Export** — export PlantUML for LLM collaboration
- **Based on COLA State Machine** — backend reuses Alibaba COLA State Machine

## Tech Stack

| Module | Technology |
| --- | --- |
| Backend | Java 1.8 · Spring Boot 2.7.14 · COLA State Machine · Lombok · Fastjson2 |
| Frontend | Vue 3 · Vite 5 · LogicFlow · Element Plus · Vue Router · Vue JSON Pretty |

## Project Structure

```
state-machine-template-factory/
├── src/                                  # Java backend
│   └── main/java/
│       ├── com/alibaba/cola/statemachine/  # COLA state machine framework
│       └── com/boommanpro/statemachine/   # Template factory application
├── ui/                                   # Vue 3 frontend
│   ├── src/
│   │   ├── components/                   # LF components, registered nodes, property config
│   │   ├── App.vue
│   │   └── main.js
│   ├── vite.config.js
│   └── package.json
├── docs/                                 # GitHub Pages deployment output
└── pom.xml
```

## Quick Start

### Prerequisites

- Node.js >= 16
- Java 1.8 (for backend, optional)
- Maven 3 (for backend build, optional)

### Frontend Development

```bash
cd ui
npm install
npm run dev
```

Open <http://localhost:5173> in your browser.

### Frontend Build

```bash
cd ui
npm install
npm run build
```

The build output goes to `ui/dist`. Since `vite.config.js` sets `base: './'`, it works directly on GitHub Pages.

### Backend (Optional)

```bash
mvn spring-boot:run
```

### Example

The project ships with an order state machine example at `src/test/resources/instance/demo/`:

- `orderTransition.json` — state transition config
- `orderTransitionGraphData.json` — graph data

## Deployment

### GitHub Pages (Automated)

A GitHub Actions workflow (`.github/workflows/deploy.yml`) is configured to automatically build `ui/` and deploy to GitHub Pages on every push to `main`.

Manual setup:

1. Go to **Settings → Pages** in the repository
2. Set **Source** to `GitHub Actions`
3. Push to `main` to trigger automatic deployment

### Manual Deployment

```bash
cd ui
npm install
npm run build
# Deploy the contents of dist to any static server
```

## Contributing

PRs are welcome. The MVP is complete, while the management console still needs work:

- CRUD for state machine generators
- Version management keyed by state machine name with diff
- Enhanced drag-and-drop generation
- Java code template generation based on `package-name` and other parameters

## License

[MIT License](LICENSE)
