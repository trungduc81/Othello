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
## 📥 Cài đặt

### 1. Clone project
```bash
git clone https://github.com/trungduc81/Othello.git
cd Othello
```

## 🚀 Chạy ứng dụng

### Bước 1: Chạy Backend (Port 8080)

**Cách 1: VS Code Run (Khuyến khích)**
1. Mở `backend/src/main/java/com/ptit/othello/Application.java` trong VS Code
2. Bấm nút ▶️ **Run** phía trên hàm `main()`
3. Hoặc dùng **Ctrl + F5**
4. Chờ đến khi thấy: `Started Application in X seconds`

**Cách 2: Command Line**
```bash
cd backend
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

> **Linux/Mac**: Thay `mvnw.cmd` bằng `./mvnw`

### Bước 2: Chạy Frontend - Terminal mới

**Cách 1: Live Server (Khuyến khích)**
1. Cài VS Code extension "Live Server" (Ritwick Dey)
2. Mở `frontend` folder trong VS Code
3. Click chuột phải vào `index.html`
4. Chọn "Open with Live Server"
5. Browser tự động mở tại `http://127.0.0.1:5500`

**Cách 2: Python**
```bash
cd frontend
python -m http.server 5000
```
Truy cập: **http://localhost:5000**

### Bước 3: Truy cập trò chơi

Browser sẽ tự động mở (Live Server) hoặc truy cập URL ở trên

## 🎮 Hướng dẫn chơi

- **Quân Đen**: Bạn (bắt đầu trước)
- **Quân Trắng**: AI
- Click vào ô có dấu chấm để đặt quân
- Nhập lại: Nhấn nút "New Game"
- Chọn độ khó: Dễ (2), Trung bình (4), Khó (6)

