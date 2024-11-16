<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="lab02.data.RequestData" %>
<%@ page import="lab02.data.ResponseData" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WebLab01</title>
    <link rel="stylesheet" href="styles.css">
    <script src="js/random-positions.js" defer></script>
    <script src="js/macos-style-windows.js" defer></script>
    <script type="module" src="js/script.js" defer></script>

</head>
<body>
<header>
    <span>P3215</span>
    <span>🪲Жук🪲И. А.</span>
    <span>ЛАБ1</span>
</header>
<div class="gifs">
    <img src="https://media.giphy.com/media/nIUN14gx8gPJB3qslB/giphy.gif" alt="Заяц"    class="rabbit"    width="400">
    <img src="https://media.giphy.com/media/1xlrc3eX9clGRaTUOs/giphy.gif" alt="черепок" class="skull"     width="400">
    <img src="https://media.giphy.com/media/2gYhrmp3qxXnGgZ0iq/giphy.gif" alt="фиджи"   class="fiji"      width="400">
    <img src="https://media.giphy.com/media/yxBpVbzM3FJpm/giphy.gif"      alt="конь"    class="red-horse" width="400">
    <img src="https://media.giphy.com/media/gzROsII7swwrm/giphy.gif"      alt="голова-дэвида" class="head-of-david"
         width="400">
    <img src="https://media.giphy.com/media/116mGX9b4zVHG/giphy.gif"      alt="розочка" class="rose"      width="400">
    <img src="https://media.giphy.com/media/H3eLO2SU8iAKqVTTGI/giphy.gif" alt="дельфин" class="dolphin"   width="400">
    <img src="https://media.giphy.com/media/37Uer6MbSlFgA/giphy.gif"      alt="скелет-думает" class="skeleton" width="400">
    <img src="https://media.giphy.com/media/huyVRDvLxGVUUoetHv/giphy.gif" alt="dvd"     class="dvd"       width="400">
    <img src="https://media.giphy.com/media/11T8aaTZZ403PW/giphy.gif"     alt="нож"     class="knife"     width="400">
    <img src="https://media.giphy.com/media/oNYYYd0T2C0ctDBWE4/giphy.gif" alt="acab"    class="acab"      width="400">

    <img src="https://media.giphy.com/media/Ve4lrYiqpVkxOCdma6/giphy.gif" alt="smile"   class="smile"     width="400">
    <img src="https://media.giphy.com/media/lnDhCusOvQEeX8oDCr/giphy.gif" alt="дискета" class="diskette"   width="400">
    <img src="https://media.giphy.com/media/50HKmWhN2urElj0viN/giphy.gif" alt="бургер"  class="burger"    width="400">
    <img src="https://media.giphy.com/media/xThtayhFCUiob1hFG8/giphy.gif" alt="ноутбук" class="laptop"     width="400">

    <!--    <img src="https://media.giphy.com/media/uid/giphy.gif" alt="" class="" width="400">-->
</div>
<div class="main-content">
    <div class="control-block">
        <div class="mac-os-control-wrapper">
            <h2>Управление</h2>
            <button id="reset-btn">Сброс</button>
            <form id="control-form">
                <div class="form-group">
                    <label for="x-values">X:</label>
                    <div id="x-values">
                        <input type="checkbox" id="x-4" name="x" value="-4"><label for="x-4">-4</label>
                        <input type="checkbox" id="x-3" name="x" value="-3"><label for="x-3">-3</label>
                        <input type="checkbox" id="x-2" name="x" value="-2"><label for="x-2">-2</label>
                        <input type="checkbox" id="x-1" name="x" value="-1"><label for="x-1">-1</label>
                        <input type="checkbox" id="x0" name="x" value="0"><label for="x0">0</label>
                        <input type="checkbox" id="x1" name="x" value="1"><label for="x1">1</label>
                        <input type="checkbox" id="x2" name="x" value="2"><label for="x2">2</label>
                        <input type="checkbox" id="x3" name="x" value="3"><label for="x3">3</label>
                        <input type="checkbox" id="x4" name="x" value="4"><label for="x4">4</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="y-value">Y:</label>
                    <input type="text" id="y-value" name="y" placeholder="Введите значение от -5 до 3">
                    <span class="error-message" id="y-error"></span> <!-- Сообщение об ошибке для Y -->
                </div>

                <div class="form-group">
                    <label for="r-value">R:</label>
                    <input type="text" id="r-value" name="r" placeholder="Введите значение (1, 4)">
                    <span class="error-message" id="r-error"></span> <!-- Сообщение об ошибке для R -->
                </div>
            </form>
            <button id="send-btn">Отправить</button>
        </div>
    </div>
    <div id="graphBlock">
        <div class="mac-os-control-wrapper">
            <img alt="график" id="graph" src="resources/graph.svg"  height="400">
        </div>
    </div>
</div>

<div id="table-over-container">
    <div id="table-container"></div>
</div>


</body>
</html>
