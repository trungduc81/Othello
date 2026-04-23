# 🎮 Game Othello AI - B23CN864

Bài tập lớn môn **Trí tuệ nhân tạo** - Implementasi Game Othello với AI Minimax

## 📋 Cấu trúc dự án

```
B23CN864/
├── frontend/                          # Giao diện web HTML/CSS/JS
│   ├── index.html                    # Bàn cờ UI
│   ├── style.css                     # Styling responsive
│   └── game.js                       # Logic game + API calls
│
└── backend/                          # Spring Boot Java
    ├── pom.xml                       # Maven dependencies
    ├── src/main/java/com/ptit/othello/
    │   ├── Application.java          # Entry point Spring Boot
    │   ├── api/
    │   │   ├── AiController.java    # REST API endpoints
    │   │   └── dto/
    │   │       ├── MoveRequest.java  # Request DTO
    │   │       ├── MoveResponse.java # Response DTO
    │   │       └── GameStateResponse.java
    │   ├── core/
    │   │   ├── Board.java            # Quản lý bàn cờ 8x8
    │   │   └── GameRules.java        # Luật game + flip logic
    │   └── ai/
    │       ├── Minimax.java          # Minimax + Alpha-Beta pruning
    │       └── Heuristic.java        # Hàm đánh giá vị trí
    └── src/main/resources/
        └── application.properties    # Cấu hình Spring Boot
```

## 🎯 Tính năng chính

### Frontend
✅ Bàn cờ 8x8 vẽ bằng Canvas  
✅ Hiển thị nước đi hợp lệ  
✅ Click để chơi  
✅ Thông tin lượt chơi real-time  
✅ Nút New Game, Pass  

### Backend - Phần AI quan trọng nhất
✅ **Minimax Algorithm** - Tìm nước đi tốt nhất  
✅ **Alpha-Beta Pruning** - Tối ưu hóa tìm kiếm  
✅ **Heuristic Function** - Đánh giá vị trí game:
   - Position Score: Góc (100), cạnh, giữa
   - Piece Differential: Chênh lệch số quân
   - Mobility Score: Số nước đi khả dụng
✅ **REST API** - 3 endpoints chính:
   - POST /api/ai/move - Lấy nước đi AI
   - POST /api/game/validate-move - Kiểm tra nước
   - POST /api/game/next-player - Xác định lượt tiếp theo

## 🚀 Cách chạy

### 1. Chạy Frontend (trên cổng 5000)
```bash
cd B23CN864/frontend
python -m http.server 5000
```
Truy cập: **http://localhost:5000**

### 2. Chạy Backend (trên cổng 8080)
```bash
cd B23CN864/backend
mvn clean install
mvn spring-boot:run
```
API: **http://localhost:8080/api**  
Health check: **http://localhost:8080/api/health**

## 🔧 Chi tiết thuật toán AI

### Minimax với Alpha-Beta Pruning
- **Độ sâu tìm kiếm**: 6 nước (có thể điều chỉnh)
- **Chiến lược**: Maximize score cho AI (WHITE), Minimize cho người chơi (BLACK)
- **Pruning**: Loại bỏ các nhánh không cần thiết → tăng tốc độ
- **Edge Cases**: Xử lý khi một player không có nước hợp lệ

### Heuristic Function
```
Score = PositionScore + PieceDifferential + MobilityScore

- PositionScore: Trọng số vị trí (góc quan trọng nhất)
- PieceDifferential: (PlayerPieces - OpponentPieces) * 2
- MobilityScore: (PlayerMoves - OpponentMoves) * 5
```

### Endgame Evaluation
- Thắng: +10000
- Thua: -10000
- Hòa: 0

## 📝 API Endpoints

### 1. Get AI Move
```
POST /api/ai/move
Content-Type: application/json

{
    "board": [[0,0,0,...],[...],...],
    "playerColor": 2  // 1=BLACK, 2=WHITE
}

Response:
{
    "move": [3, 3],
    "message": "Nước đi của AI",
    "success": true
}
```

### 2. Validate Move
```
POST /api/game/validate-move
{
    "board": [[...],...],
    "playerColor": 1
}

Response:
{
    "board": [[...],...],
    "validMoves": [[...],[...],...],
    "currentPlayer": 1,
    "blackCount": 5,
    "whiteCount": 4,
    "gameOver": false
}
```

### 3. Next Player
```
POST /api/game/next-player
{
    "board": [[...],...],
    "playerColor": 1
}
```

## 🎓 Kiến thức liên quan

- **Game Theory**: Min-max principle
- **Algorithm**: Tree Search, Pruning
- **Heuristics**: Position evaluation
- **Data Structure**: 2D array for board
- **API Design**: RESTful service
- **Testing**: Alpha-Beta pruning efficiency

## 📊 Độ phức tạp

- **Time Complexity**: O(b^d) → O(b^(d/2)) với Alpha-Beta pruning
  - b = branching factor (~10 nước hợp lệ)
  - d = depth (6)
- **Space Complexity**: O(d) - Call stack

## ⚙️ Tùy chỉnh

### Thay đổi độ khó AI
File: `AiController.java`, dòng `minimax.setMaxDepth(6)`
- Depth 4: Dễ
- Depth 6: Bình thường
- Depth 8: Khó
- Depth 10+: Rất khó (chậm)

### Thay đổi trọng số Heuristic
File: `Heuristic.java`
- Chỉnh `POSITION_WEIGHTS` matrix
- Chỉnh hệ số: `* 2`, `* 5`

## 🐛 Troubleshooting

**Lỗi: "Không thể kết nối tới backend"**
- Đảm bảo backend đang chạy trên port 8080
- Kiểm tra CORS được enable trong `application.properties`

**AI chậm quá**
- Giảm `maxDepth` từ 6 xuống 4 hoặc 5
- Tăng Alpha-Beta pruning effectiveness

**Game logic sai**
- Kiểm tra `GameRules.java` - logic lật cờ
- Kiểm tra `isValidMove()` - kiểm tra nước hợp lệ

## 👨‍💻 Senior Java Developer Notes

1. **Clean Code**: Sử dụng static methods cho GameRules (stateless)
2. **Design Pattern**: 
   - DTO pattern cho API
   - Strategy pattern cho Minimax/Heuristic
   - Board as state holder
3. **Performance**: Alpha-Beta pruning cực kỳ quan trọng
4. **Testing**: Cần test edge cases (no valid moves, last move, etc.)

---

**Ghi chú**: Đây là phần backend **hoàn chỉnh** với AI Minimax chi tiết. Frontend có thể mở rộng thêm animation, sound effects, v.v.
