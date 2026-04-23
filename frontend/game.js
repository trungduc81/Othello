// ==========================================
// PHẦN 1: CÁC BIẾN TOÀN CỤC & CẤU HÌNH
// ==========================================
const canvas = document.getElementById('gameBoard');
const ctx = canvas.getContext('2d');
const BOARD_SIZE = 8;
const CELL_SIZE = 60;
const API_URL = 'http://localhost:8080/api'; // Backend API của Spring Boot

const EMPTY = 0;
const BLACK = 1;
const WHITE = 2;

let board = [];
let currentPlayer = BLACK;
let validMoves = [];
let gameActive = true;
let moveCount = 1; 
let lastMove = null; 

// ==========================================
// PHẦN 2: KHỞI TẠO VÀ XỬ LÝ GIAO DIỆN (UI)
// ==========================================
function initBoard() {
    board = Array(BOARD_SIZE).fill(null).map(() => Array(BOARD_SIZE).fill(EMPTY));
    board[3][3] = WHITE;
    board[3][4] = BLACK;
    board[4][3] = BLACK;
    board[4][4] = WHITE;
    
    currentPlayer = BLACK;
    gameActive = true;
    moveCount = 1; 
    lastMove = null; 
    validMoves = getValidMoves(currentPlayer);
    
    document.getElementById('historyList').innerHTML = '';
    document.getElementById('status').textContent = '';
    
    updateScore();
    drawBoard();
    syncPanelHeight();
}

function drawBoard() {
    ctx.fillStyle = '#009067'; 
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    
    ctx.strokeStyle = '#000';
    ctx.lineWidth = 2;
    
    // Vẽ lưới
    for (let i = 0; i <= BOARD_SIZE; i++) {
        ctx.beginPath();
        ctx.moveTo(i * CELL_SIZE, 0);
        ctx.lineTo(i * CELL_SIZE, canvas.height);
        ctx.stroke();
        
        ctx.beginPath();
        ctx.moveTo(0, i * CELL_SIZE);
        ctx.lineTo(canvas.width, i * CELL_SIZE);
        ctx.stroke();
    }
    
    // Vẽ dấu chấm trung tâm
    ctx.fillStyle = '#000';
    const dots = [[2, 2], [2, 6], [6, 2], [6, 6]];
    dots.forEach(([row, col]) => {
        ctx.fillRect(col * CELL_SIZE + CELL_SIZE / 2 - 3, row * CELL_SIZE + CELL_SIZE / 2 - 3, 6, 6);
    });
    
    // Vẽ quân cờ
    for (let row = 0; row < BOARD_SIZE; row++) {
        for (let col = 0; col < BOARD_SIZE; col++) {
            const piece = board[row][col];
            if (piece !== EMPTY) {
                drawPiece(row, col, piece);
            }
        }
    }
    
    // Vẽ gợi ý nước đi
    if (currentPlayer === BLACK && gameActive) {
        validMoves.forEach(([row, col]) => {
            ctx.fillStyle = 'rgba(0, 0, 0, 0.3)';
            ctx.beginPath();
            ctx.arc(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2, 8, 0, Math.PI * 2);
            ctx.fill();
        });
    }

    // Vẽ highlight viền đỏ
    if (lastMove !== null) {
        const x = lastMove.col * CELL_SIZE + CELL_SIZE / 2;
        const y = lastMove.row * CELL_SIZE + CELL_SIZE / 2;
        const radius = CELL_SIZE / 2 - 5; 

        ctx.beginPath();
        ctx.arc(x, y, radius, 0, Math.PI * 2);
        ctx.strokeStyle = '#ff3333'; 
        ctx.lineWidth = 3;           
        ctx.stroke();
    }
}

function drawPiece(row, col, player) {
    const x = col * CELL_SIZE + CELL_SIZE / 2;
    const y = row * CELL_SIZE + CELL_SIZE / 2;
    const radius = CELL_SIZE / 2 - 5;
    
    ctx.beginPath();
    ctx.arc(x, y, radius, 0, Math.PI * 2);
    ctx.fillStyle = player === BLACK ? '#000' : '#fff';
    ctx.fill();
    ctx.strokeStyle = '#333';
    ctx.lineWidth = 2;
    ctx.stroke();
}

function updateScore() {
    let blackCount = 0, whiteCount = 0;
    for (let row = 0; row < BOARD_SIZE; row++) {
        for (let col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col] === BLACK) blackCount++;
            else if (board[row][col] === WHITE) whiteCount++;
        }
    }
    document.getElementById('blackScore').textContent = blackCount;
    document.getElementById('whiteScore').textContent = whiteCount;
    
    const turnText = currentPlayer === BLACK ? '● Lượt của Đen' : '○ Lượt của Máy (Đang nghĩ...)';
    document.getElementById('turnInfo').textContent = turnText;
}

function getNotation(row, col) {
    const letters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
    const letter = letters[col];
    const number = row + 1;
    return `${letter}${number}`;
}

function addMoveToHistory(row, col, player) {
    const historyList = document.getElementById('historyList');
    const li = document.createElement('li');
    
    const notation = (row !== null && col !== null) ? getNotation(row, col) : 'Pass';
    
    li.className = player === BLACK ? 'black-move' : 'white-move';
    li.textContent = `${moveCount}: ${notation}`;
    historyList.appendChild(li);
    
    const historyContent = document.querySelector('.history-content');
    historyContent.scrollTop = historyContent.scrollHeight;
    moveCount++;
}

function updateAiLabel() {
    const difficultySelect = document.getElementById('difficulty');
    const aiLabel = document.getElementById('aiLabel');
    if (difficultySelect && aiLabel) {
        const selectedText = difficultySelect.options[difficultySelect.selectedIndex].text;
        const difficultyName = selectedText.split(' (')[0]; 
        aiLabel.textContent = `White (AI - ${difficultyName})`;
    }
}

function syncPanelHeight() {
    const gameContainer = document.querySelector('.game-container');
    const historyPanel = document.querySelector('.history-panel');
    if (gameContainer && historyPanel) {
        historyPanel.style.height = gameContainer.offsetHeight + 'px';
    }
}

// ==========================================
// PHẦN 3: LOGIC TRÒ CHƠI & LUẬT LỆ LẬT CỜ
// ==========================================
function makeMove(row, col, player) {
    const opponent = player === BLACK ? WHITE : BLACK;
    
    if (!validMoves.some(m => m[0] === row && m[1] === col)) return;
    
    board[row][col] = player;
    lastMove = { row: row, col: col };
    flipPieces(row, col, player, opponent);
    addMoveToHistory(row, col, player);
    
    currentPlayer = opponent;
    validMoves = getValidMoves(currentPlayer);
    
    if (validMoves.length === 0) {
        addMoveToHistory(null, null, currentPlayer); 
        currentPlayer = player; 
        validMoves = getValidMoves(currentPlayer);
        
        if (validMoves.length === 0) {
            updateScore();
            drawBoard();
            endGame();
            return;
        }
    }
    
    updateScore();
    drawBoard();
    
    if (currentPlayer === WHITE && gameActive) {
        setTimeout(() => { getAiMove(); }, 500);
    }
}

function flipPieces(row, col, player, opponent) {
    const directions = [[-1, 0], [1, 0], [0, -1], [0, 1], [-1, -1], [-1, 1], [1, -1], [1, 1]];
    for (const [dRow, dCol] of directions) {
        const toFlip = [];
        let r = row + dRow;
        let c = col + dCol;
        
        while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE) {
            if (board[r][c] === EMPTY) break;
            
            if (board[r][c] === opponent) {
                toFlip.push([r, c]);
            } else if (board[r][c] === player) {
                toFlip.forEach(([fr, fc]) => { board[fr][fc] = player; });
                break;
            }
            r += dRow;
            c += dCol;
        }
    }
}

function getValidMoves(player) {
    const moves = [];
    const opponent = player === BLACK ? WHITE : BLACK;
    for (let row = 0; row < BOARD_SIZE; row++) {
        for (let col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col] === EMPTY && isValidMove(row, col, player, opponent)) {
                moves.push([row, col]);
            }
        }
    }
    return moves;
}

function isValidMove(row, col, player, opponent) {
    const directions = [[-1, 0], [1, 0], [0, -1], [0, 1], [-1, -1], [-1, 1], [1, -1], [1, 1]];
    for (const [dRow, dCol] of directions) {
        if (canFlipInDirection(row, col, dRow, dCol, player, opponent)) return true;
    }
    return false;
}

function canFlipInDirection(row, col, dRow, dCol, player, opponent) {
    let r = row + dRow;
    let c = col + dCol;
    let hasOpponent = false;
    
    while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE) {
        if (board[r][c] === EMPTY) return false;
        if (board[r][c] === opponent) hasOpponent = true;
        else if (board[r][c] === player && hasOpponent) return true;
        else return false;
        
        r += dRow;
        c += dCol;
    }
    return false;
}

function endGame() {
    gameActive = false;
    let blackCount = 0, whiteCount = 0;
    
    for (let row = 0; row < BOARD_SIZE; row++) {
        for (let col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col] === BLACK) blackCount++;
            else if (board[row][col] === WHITE) whiteCount++;
        }
    }
    
    const statusDiv = document.getElementById('status');
    if (blackCount > whiteCount) {
        statusDiv.textContent = `🎉 BẠN THẮNG! (${blackCount} - ${whiteCount})`;
        statusDiv.style.color = "#007bff";
    } else if (whiteCount > blackCount) {
        statusDiv.textContent = `🤖 AI THẮNG! (${whiteCount} - ${blackCount})`;
        statusDiv.style.color = "#dc3545";
    } else {
        statusDiv.textContent = `🤝 HÒA NHAU! (${blackCount} - ${whiteCount})`;
        statusDiv.style.color = "#1a1a1a";
    }
    statusDiv.style.fontSize = "1.5rem";
    statusDiv.style.fontWeight = "bold";
    statusDiv.style.marginTop = "20px";
}

// ==========================================
// PHẦN 4: KẾT NỐI API BACKEND
// ==========================================
async function getAiMove() {
    const difficultySelect = document.getElementById('difficulty');
    const selectedDepth = difficultySelect ? parseInt(difficultySelect.value) : 6;
    try {
        const response = await fetch(`${API_URL}/ai/calculate-move`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                board: board,
                aiRole: WHITE,
                depth: selectedDepth 
            })
        });
        
        if (!response.ok) throw new Error(`Lỗi kết nối Server! status: ${response.status}`);
        
        const data = await response.json();
        if (data && data.bestMove) {
            makeMove(data.bestMove.row, data.bestMove.col, WHITE);
        } else {
            console.error("Backend không trả về bestMove hợp lệ:", data);
            document.getElementById('status').textContent = 'AI bỏ cuộc vì lỗi thuật toán!';
        }
    } catch (error) {
        console.error('Lỗi gọi API:', error);
        document.getElementById('status').textContent = 'Lỗi: Không thể kết nối với Backend Spring Boot. Đã bật server chưa?';
    }
}

// ==========================================
// PHẦN 5: LẮNG NGHE SỰ KIỆN & KHỞI CHẠY LẦN ĐẦU
// ==========================================

// Sự kiện click lên bàn cờ
canvas.addEventListener('click', (e) => {
    if (currentPlayer !== BLACK || !gameActive) return;
    
    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;
    
    const col = Math.floor(x / CELL_SIZE);
    const row = Math.floor(y / CELL_SIZE);
    
    if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
        makeMove(row, col, BLACK);
    }
});

// Sự kiện click nút bấm và thay đổi menu
document.getElementById('newGameBtn').addEventListener('click', initBoard);
document.getElementById('difficulty').addEventListener('change', updateAiLabel);

// Chạy các lệnh khởi tạo ngay khi trình duyệt vừa load xong file JS
initBoard();
updateAiLabel();