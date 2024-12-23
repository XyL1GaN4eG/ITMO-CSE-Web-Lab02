//table.js

function generateTable(data) {
    const tableContainer = document.getElementById("table-container");
    let table = tableContainer.querySelector("table");

    if (!table) {
        table = document.createElement("table");
        table.classList.add("data-table");

        const headerRow = document.createElement("tr");
        Object.keys(data[0]).forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });
        table.appendChild(headerRow);
        tableContainer.appendChild(table);
    }

    data.forEach(rowData => addRowToTable(rowData, table));
}

function addRowToTable(rowData, table) {
    const row = document.createElement("tr");
    Object.values(rowData).forEach(cellData => {
        const cell = document.createElement("td");
        cell.textContent = cellData;
        row.appendChild(cell);
    });
    table.appendChild(row);
}

//graph.js

const pointsByR = {};

let board = createBoard(1); // Инициализация доски

let graph1, graph2, graph3, i1, i2, i3;

// Функция для создания новой доски
function createBoard() {
    const piece = 3;
    return JXG.JSXGraph.initBoard("jxgbox", {
        boundingbox: [-piece, piece, piece, -piece],
        axis: true
    });
}

function redrawGraphs(radio) {
    // Удаляем старую доску
    JXG.JSXGraph.freeBoard(board);

    // Создаем новую доску с новыми параметрами
    let rIn = parseFloat(radio);
    if (rIn >= 1 && rIn <= 4) {
        board = createBoard();
    } else {
        radio = 1;
        board = createBoard();
    }

    var graphColor = 'blue';

    board.on('down', handleDown);

    if (graph1) board.removeObject(graph1);
    if (i1) board.removeObject(i1);
    if (graph2) board.removeObject(graph2);
    if (i2) board.removeObject(i2);
    if (graph3) board.removeObject(graph3);
    if (i3) board.removeObject(i3);

    //triangle
    graph1 = board.create('functiongraph', [function (x) {
        return 2 * x - radio;  // Уравнение прямой f(x) = 2x - 1
    }, 0, radio / 2]);  // Указываем диапазон x от -5 до 5

    i1 = board.create('integral', [[0, (radio / 2)], graph1], {
        label: {visible: false},
        curveLeft: {
            visible: false
        },
        curveRight: {
            visible: false
        },
        baseRight: {
            visible: false
        },
        baseLeft: {
            visible: false
        },
        fillColor: graphColor,
    });

    //square
    graph2 = board.create('functiongraph', [function (x) {
        return -radio / 2
    }, -radio, 0]);
    i2 = board.create('integral', [[-radio, 0], graph2], {
        label: {visible: false},
        curveLeft: {
            visible: false
        },
        curveRight: {
            visible: false
        },
        baseRight: {
            visible: false
        },
        baseLeft: {
            visible: false
        },
        fillColor: graphColor,
    });

    //circle
    graph3 = board.create('functiongraph', [function (x) {
        return Math.sqrt(radio * radio - x * x);
    }, 0, radio]);
    i3 = board.create('integral', [[0, radio], graph3], {
        label: {visible: false},
        curveLeft: {
            visible: false
        },
        curveRight: {
            visible: false
        },
        baseRight: {
            visible: false
        },
        baseLeft: {
            visible: false
        },
        fillColor: graphColor,
    });

    const currentR = parseFloat(radio);
    if (pointsByR[currentR]) {
        pointsByR[currentR].forEach(point => {
            board.create('point', [point.x, point.y], {
                color: point.isIn ? 'green' : 'red',
                label: {visible: false},
            });
        });
    }

}

var drawPoint = function (responseData) {
    redrawGraphs(rInput.value)
    const currentR = parseFloat(rInput.value);

    if (!pointsByR[currentR]) {
        pointsByR[currentR] = [];
    }

    // Добавляем новые точки для текущего R
    responseData.forEach(jsonElement => {
        const point = {
            x: jsonElement.x,
            y: jsonElement.y,
            isIn: jsonElement.isIn
        };
        pointsByR[currentR].push(point);

        const pointObj = board.create('point', [point.x, point.y], {
            color: point.isIn ? 'green' : 'red'
        });
    });
};


//events.js
function setupEventListeners(sendBtn, yInput, rInput, xCheckboxes, yError, rError) {
    let yTouched = false;
    let rTouched = false;
    sendBtn.disabled = true;

    yInput.addEventListener('input', () => {
        yTouched = true;
        const {
            isYValid,
            isRValid,
            isXValid
        } = validateInputs(yInput, yError, rInput, rError, xCheckboxes, yTouched, rTouched);
        sendBtn.disabled = !(isYValid && isRValid && isXValid);
    });

    rInput.addEventListener('input', () => {
        rTouched = true;
        const {
            isYValid,
            isRValid,
            isXValid
        } = validateInputs(yInput, yError, rInput, rError, xCheckboxes, yTouched, rTouched);
        sendBtn.disabled = !(isYValid && isRValid && isXValid);
        redrawGraphs(rInput.value);
    });

    xCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            const {
                isYValid,
                isRValid,
                isXValid
            } = validateInputs(yInput, yError, rInput, rError, xCheckboxes, yTouched, rTouched);
            sendBtn.disabled = !(isYValid && isRValid && isXValid);
        });
    });
}

// script.js

import {validateInputs} from './validation.js';

// Инициализация переменных и элементов на странице
const sendBtn = document.getElementById('send-btn');
const yInput = document.getElementById('y-value');
const rInput = document.getElementById('r-value');
const yError = document.getElementById('y-error');
const rError = document.getElementById('r-error');
const xCheckboxes = document.querySelectorAll('#x-values input[type="checkbox"]');
const url = "/lab02-1.0-SNAPSHOT/controller";

redrawGraphs(rInput.value);


async function sendData(xArray, y) {
    let r = "1.0"; // Устанавливаем значение по умолчанию для R

    try {
        if (rInput.value.trim() !== "") { // Проверяем, введено ли значение R
            r = rInput.value.trim(); // Берем значение из rInput
        }
    } catch (error) {
        console.error("Ошибка при обработке значения R:", error);
    }

    // Создаем объект с нужной структурой
    let obj = {
        x_array: xArray.map(x => parseFloat(x).toFixed(3)), // Преобразуем массив x в массив строк
        y: parseFloat(y).toFixed(3),             // Преобразуем y в строку
        r: r                 // Используем строковое значение R
    };

    console.log("жсончик реквеста");
    console.log(JSON.stringify(obj, null, 2)); // Красиво форматируем JSON для отладки

    let response = await fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(obj) // Отправляем сформированный JSON
    });
    var responseData = await response.json();
    drawPoint(responseData)
    generateTable(responseData)
}


// Использование функции в обработчике события
sendBtn.addEventListener("click", async (event) => {
    event.preventDefault();
    let x_values = [];
    document.querySelectorAll('#x-values > input:checked').forEach((element) => {
        x_values.push(element.value);
    });

    // Вызов вынесенной функции
    await sendData(x_values, yInput.value, rInput.value);
});

// Валидация значений формы
validateInputs(yInput, yError, rInput, rError, xCheckboxes, false, false);

window.addEventListener('load', fetchOnLoad);

// Обработчик загрузки данных
async function fetchOnLoad() {
    try {
        let response = await fetch(url, {
            method: "GET"
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const responseData = await response.json();

        console.log("Загруженные данные:", responseData);

        // Генерируем таблицу
        generateTable(responseData);

        // Обновляем график
        const currentR = parseFloat(rInput.value);
        if (!pointsByR[currentR]) {
            pointsByR[currentR] = [];
        }

        // Добавляем точки в массив и рисуем их на графике
        responseData.forEach(pointData => {
            const point = {
                x: pointData.x,
                y: pointData.y,
                isIn: pointData.isIn
            };
            pointsByR[currentR].push(point);

            // Рисуем точку на графике
            board.create('point', [point.x, point.y], {
                color: point.isIn ? 'green' : 'red',
                label: { visible: false }
            });
        });
    } catch (error) {
        console.error("Ошибка при загрузке данных:", error);
    }
}


// Обработчик отправки формы
sendBtn.addEventListener("click", async (event) => {
    event.preventDefault();
    let x_values = [];
    document.querySelectorAll('#x-values > input:checked').forEach((element) => {
        x_values.push(element.value);
    });

    await sendData(
        x_values,
        yInput.value)

    // generateTable(responseData);
    // drawPoints(responseData);

});

// Сбрасываем таблицу
const resetBtn = document.getElementById('reset-btn');
resetBtn.addEventListener('click', async () => {
    const tableContainer = document.getElementById("table-container");
    tableContainer.innerHTML = "";

    await fetch(url, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
    });
});

// Устанавливаем обработчики событий
setupEventListeners(sendBtn, yInput, rInput, xCheckboxes, yError, rError);

// Отключаем прокрутку
document.body.style.overflow = 'hidden';

var getMouseCoords = function (e, i) {
        var pos = board.getMousePosition(e, i);
        return new JXG.Coords(JXG.COORDS_BY_SCREEN, pos, board);
    },

    handleDown = async function (e) {
        if (rInput.value >= 1 && rInput.value <= 4) {
            const currentR = parseFloat(rInput.value);
            if (!pointsByR[currentR]) {
                pointsByR[currentR] = [];
            }

            let i, coords;
            if (e[JXG.touchProperty]) {
                i = 0; // Индекс пальца для сенсорных устройств
            }

            coords = getMouseCoords(e, i);

            // Сохраняем точку
            pointsByR[currentR].push({
                x: coords.usrCoords[1],
                y: coords.usrCoords[2],
                isIn: null // Это значение можно будет обновить после ответа сервера
            });

            // Отправляем запрос на сервер
            const response = await sendData([coords.usrCoords[1]], coords.usrCoords[2]);
            response.forEach(jsonElement => {
                const point = board.create('point', [jsonElement.x, jsonElement.y], {
                    color: jsonElement.isIn ? 'green' : 'red'
                });

                // Обновляем информацию о попадании точки в область
                const pointIndex = pointsByR[currentR].findIndex(p => p.x === jsonElement.x && p.y === jsonElement.y);
                if (pointIndex !== -1) {
                    pointsByR[currentR][pointIndex].isIn = jsonElement.isIn;
                }
            });
        }
    };
