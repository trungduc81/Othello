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

## 🛠️ Yêu cầu hệ thống

- **Java**: 8+
- **Maven**: 3.6+
- **Python**: 3.7+ (chạy frontend server)
- **Git**: Để clone project

## 📥 Cài đặt

### Clone project
```bash
git clone https://github.com/trungduc81/Othello.git
cd Othello
```

### Backend - Build
```bash
cd backend
mvn clean install
```

### Frontend - Không cần install (HTML/CSS/JS thuần)

## 🚀 Chạy ứng dụng

### Terminal 1: Chạy Backend (Port 8080)
```bash
cd backend
mvn spring-boot:run
```
Hoặc chạy JAR sau khi build:
```bash
java -jar target/othello-ai-1.0.0.jar
```

### Terminal 2: Chạy Frontend (Port 5000)
```bash
cd frontend
python -m http.server 5000
```

### Truy cập trò chơi
Mở browser: **http://localhost:5000**

## 🎮 Cách chơi

- **Quân Đen**: Bạn (người chơi)
- **Quân Trắng**: AI
- Click vào ô có dấu chấm để đặt quân
- AI tự động đi sau khi bạn đi
- Chọn độ khó: Dễ (2), Trung bình (4), Khó (6)

## 📂 File quan trọng

| File | Chức năng |
|------|----------|
| `Minimax.java` | Thuật toán Minimax + Alpha-Beta Pruning |
| `Heuristic.java` | Hàm đánh giá vị trí trò chơi |
| `GameRules.java` | Luật Othello + kiểm tra nước hợp lệ |
| `Board.java` | Quản lý trạng thái bàn cờ |
| `AiController.java` | REST API cho AI |
| `game.js` | Frontend logic |

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
