# 🎮 Othello AI Game

Trò chơi Othello (Reversi) với AI sử dụng thuật toán Minimax và Alpha-Beta Pruning.

## 📁 Cấu trúc dự án

```
Othello/
├── frontend/
│   ├── index.html      - Giao diện bàn cờ
│   ├── style.css       - CSS styling
│   └── game.js         - Logic trò chơi
│
└── backend/
    ├── pom.xml         - Maven config
    └── src/main/java/com/ptit/othello/
        ├── Application.java
        ├── api/
        │   ├── AiController.java
        │   └── dto/
        ├── core/
        │   ├── Board.java
        │   └── GameRules.java
        └── ai/
            ├── Minimax.java
            └── Heuristic.java
```


