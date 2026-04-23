# 🎮 Othello AI Game

Trò chơi Othello (Reversi) với AI sử dụng thuật toán Minimax và Alpha-Beta Pruning.

## 📁 Cấu trúc dự án

```
Othello/
├── backend/
│   ├── .mvn/wrapper/                  - Maven Wrapper
│   ├── pom.xml                        - Maven dependencies
│   └── src/main/
│       ├── java/com/ptit/othello/
│       │   ├── Application.java       - Entry point
│       │   ├── ai/                    - Thuật toán AI & heuristic
│       │   │   ├── Minimax.java       - Minimax + Alpha-Beta Pruning
│       │   │   └── Heuristic.java     - Hàm đánh giá vị trí
│       │   ├── api/                   - REST API endpoints
│       │   │   ├── AiController.java
│       │   │   └── dto/               - Data Transfer Objects
│       │   │       ├── GameStateResponse.java
│       │   │       ├── MoveRequest.java
│       │   │       └── MoveResponse.java
│       │   └── core/                  - Core game logic
│       │       ├── Board.java         - Quản lý bàn cờ
│       │       └── GameRules.java     - Luật game + flip logic
│       └── resources/
│           └── application.properties
│
└── frontend/                          - Web UI (HTML/CSS/JS)
    ├── index.html                     
    ├── game.js                        
    └── style.css                      
```
