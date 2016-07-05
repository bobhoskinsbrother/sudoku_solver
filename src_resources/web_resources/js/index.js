"use strict";
var makeVisitable = function (array) {
    if (array.accept === undefined) {
        array.accept = function (visitor) {
            for (var i = 0; i < this.length; i++) {
                var current = this[i];
                visitor.visit(i, current);
            }
        };
    }
};
var SudokuModel = (function () {
    var _model = [];
    makeVisitable(_model);
    var add = function add(index, cell) {
        _model[index] = cell
    };
    var accept = function (visitor) {
        _model.accept(visitor);
    };
    return {"accept": accept, "add": add};
})();
var SudokuView = (function () {
    var paintGridVisitor = function paintGridVisitor(targetDiv) {
        var $target = $("#" + targetDiv);
        return {
            "visit": function visit(i, value) {
                if ((i % 9) == 0 && i !== 0) {
                    $target.append($("<br style=\"clear:both\"/>"));
                }
                var $element;
                if (value.state === "preset") {
                    $element = $("<div></div>");
                    var $span = $("<span></span>");
                    $span.text(value.val + "");
                    $element.append($span);
                } else if (value.state === "playable") {
                    $element = $("<input type=\"text\"/>");
                    $element.change(SudokuController.handleChangeOfValue);
                    $element.val("");
                }
                $element.attr("id", "_" + i);
                $element.attr("title", i + "");
                $element.attr("data-index", i + "");
                $element.attr("class", "cell");
                $target.append($element);
            }
        };
    };

    var paintAnyIssuesVisitor = function paintAnyIssuesVisitor() {
        return {
            "visit": function visit(i, cell) {
                if(cell.valid===false) {
                    $("#_" + cell.index).addClass("error");
                } else {
                    $("#_" + cell.index).removeClass("error");
                }
            }
        };
    };
    return {
        "paintGridVisitor": paintGridVisitor,
        "paintAnyIssuesVisitor": paintAnyIssuesVisitor
    }
})();
var SudokuController = (function () {
    var playReply = function playReply(data) {
        var cells = data.cells;
        makeVisitable(cells);
        cells.accept(SudokuView.paintAnyIssuesVisitor());
        if(data.complete) {
            window.alert("Awesome, you've finished!");
            window.location.reload();
        }
    };
    var attemptPlay = function attemptPlay(index, value) {
        var requestValue = JSON.stringify({"index": index, "value": value});
        $.ajax({
            "type": "POST",
            "url": "/v1/sudoku/grid",
            "data": requestValue,
            "success": function (data) {
                playReply(data);
            },
            "dataType": "json",
            "contentType": "application/json"
        });
    };

    var removeValueAt = function removeValueAt(index) {
        $("#_"+index).val("");
    };

    var handleChangeOfValue = function handleChangeOfValue(event) {
        var input = $(event.target);
        var valString = input.val();
        var index = +(input.data().index);
        if ($.isNumeric(valString)) {
            attemptPlay(index, +(valString));
        } else {
            removeValueAt(index);
        }
    };
    var init = function init(targetDiv) {
        $.getJSON("/v1/sudoku/grid", function (data) {
            data.forEach(function (element, index, data) {
                SudokuModel.add(index, element);
            });
            SudokuModel.accept(SudokuView.paintGridVisitor(targetDiv))
        });
    };
    return {
        "init": init,
        "handleChangeOfValue": handleChangeOfValue
    };
})();
