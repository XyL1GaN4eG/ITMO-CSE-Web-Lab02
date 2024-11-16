// main.js
import {generateTable} from './table.js';
import {validateInputs} from './validation.js';
import {setupEventListeners} from './events.js';

const sendBtn = document.getElementById('send-btn');
const yInput = document.getElementById('y-value');
const rInput = document.getElementById('r-value');
const yError = document.getElementById('y-error');
const rError = document.getElementById('r-error');
const xCheckboxes = document.querySelectorAll('#x-values input[type="checkbox"]');
const url = "/fcgi-bin/hello-world.jar";

validateInputs(yInput, yError, rInput, rError, xCheckboxes, false, false);

window.addEventListener('load', fetchOnLoad);

/*
todo:
 Разработанная страница JSP должна содержать:
 "Шапку", содержащую ФИО студента, номер группы и номер варианта.                                    DONE
 Форму, отправляющую данные на сервер.                                                              PLUSMINUS
 Набор полей для задания координат точки и радиуса области в соответствии с вариантом задания.         DONE
 Сценарий на языке JavaScript, осуществляющий валидацию значений, вводимых пользователем в поля формы. DONE
 NEED TO USE jsxgraph
 Интерактивный элемент, NOT
  содержащий изображение области на координатной плоскости (в соответствии с вариантом задания) и реализующий следующую функциональность:
 Если радиус области установлен, клик курсором мыши по изображению должен обрабатываться JavaScript-функцией, определяющей координаты точки, по которой кликнул пользователь и отправляющей полученные координаты на сервер для проверки факта попадания.
 В противном случае, после клика по картинке должно выводиться сообщение о невозможности определения координат точки.
 После проверки факта попадания точки в область изображение должно быть обновлено с учётом результатов этой проверки (т.е., на нём должна появиться новая точка).
 Таблицу с результатами предыдущих проверок. Список результатов должен браться из контекста приложения, HTTP-сессии или Bean-компонента в зависимости от варианта.
 */

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