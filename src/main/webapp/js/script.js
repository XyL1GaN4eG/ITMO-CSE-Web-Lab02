//graph.js

let radio = 1;
let pointsByR = {};
let board = JXG.JSXGraph.initBoard("jxgbox", {
    boundingbox: [-2, 2, 2, -2],
    axis: true
});

let graph1, graph2, graph3, i1, i2, i3;


function redrawGraphs() {


    if (graph1) board.removeObject(graph1);
    if (i1) board.removeObject(i1);
    if (graph2) board.removeObject(graph2);
    if (i2) board.removeObject(i2);
    if (graph3) board.removeObject(graph3);
    if (i3) board.removeObject(i3);


    graph1 = board.create('functiongraph', [function (x) {
        return 2 * x - 1;  // Уравнение прямой f(x) = 2x - 1
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
        fillColor: 'blue',
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
        fillColor: 'blue',
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
        fillColor: 'blue',
    });
    for (let r in pointsByR) {
        pointsByR[r].forEach(point => point.setAttribute({visible: false}));
    }


    if (pointsByR[radio]) {
        pointsByR[radio].forEach(point => point.setAttribute({visible: true}));
    }
}


redrawGraphs();




// script.js

import {generateTable} from './table.js';
import {validateInputs} from './validation.js';
import {setupEventListeners} from './events.js';

// Инициализация переменных и элементов на странице
const sendBtn = document.getElementById('send-btn');
const yInput = document.getElementById('y-value');
const rInput = document.getElementById('r-value');
const yError = document.getElementById('y-error');
const rError = document.getElementById('r-error');
const xCheckboxes = document.querySelectorAll('#x-values input[type="checkbox"]');
const url = "/fcgi-bin/hello-world.jar";

// Валидация значений формы
validateInputs(yInput, yError, rInput, rError, xCheckboxes, false, false);

window.addEventListener('load', fetchOnLoad);

// Обработчик загрузки данных
async function fetchOnLoad() {
    let response = await fetch(url, {
        method: "GET"
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    const responseData = await response.json();

    generateTable(responseData);
}

// Обработчик отправки формы
sendBtn.addEventListener("click", async (event) => {
    event.preventDefault();
    let x_values = [];
    document.querySelectorAll('#x-values > input:checked').forEach((element) => {
        x_values.push(element.value);
    });

    let obj = {
        x_array: x_values,
        y: yInput.value,
        r: rInput.value
    };

    let response = await fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(obj)
    });

    const responseData = await response.json();
    generateTable(responseData);
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

function validatePoint(point) {

}

var getMouseCoords = function (e, i) {
        var pos = board.getMousePosition(e, i);
        return new JXG.Coords(JXG.COORDS_BY_SCREEN, pos, board);
    },

    handleDown = function (e) {
        var canCreate = true,
            i, coords, el;

        if (e[JXG.touchProperty]) {
            // index of the finger that is used to extract the coordinates
            i = 0;
        }
        coords = getMouseCoords(e, i);

        for (el in board.objects) {
            if (JXG.isPoint(board.objects[el]) && board.objects[el].hasPoint(coords.scrCoords[1], coords.scrCoords[2])) {
                canCreate = false;
                break;
            }
        }
        console.log(coords.usrCoords);

        if (canCreate) {
            // Создаем точку и добавляем её в глобальный массив
            var point = board.create('point', [coords.usrCoords[1], coords.usrCoords[2]]);
            console.log(point)
        }
    };

board.on('down', handleDown);

