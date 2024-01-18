function removeQuestionAnswer(id){
var token = $("meta[name='_csrf']").attr("content");
$.ajax({
type: "POST",
contentType: "application/json",
headers: {"X-CSRF-TOKEN": token},
url:"/qurancounteradmin/admin/questionAnswer/removeQuestionAnswer/"+id,
success: function (data) {
if (data && data.includes("success") ) {
$('#successModal').modal('show');

if (window.location.search) {

$('#successModal').modal('show');
}

}
},
error: function (e) {
var newDiv = $(document.createElement('div'));
newDiv.html(e);
newDiv.dialog();
}}); }


function startFullIndexQuestionAnswerIndex(){
var token = $("meta[name='_csrf']").attr("content");
$.ajax({
type: "POST",
contentType: "application/json",
headers: {"X-CSRF-TOKEN": token},
url:"/qurancounteradmin/admin/questionAnswer/startFullIndexQuestionAnswerIndex",
success: function (data) {
if (data && data.includes("success") ) {

$('#successModal').modal('show');
}
},
error: function (e) {
var newDiv = $(document.createElement('div'));
newDiv.html(e);
newDiv.dialog();
}}); }


if ($("#questionAnswer-add-question-editor-container") && $("#questionAnswer-add-question-editor-container").length > 0 ) {
var snowAddViewQuestion = new Quill("#questionAnswer-add-question-editor-container", { 
theme: 'snow',
modules: {
table: true,
}
});
var questionAnswerAddQuestionTable=snowAddViewQuestion.getModule('table');
document.querySelector('#addViewInsertQuestionTable').addEventListener('click', function() {
questionAnswerAddQuestionTable.insertTable(2, 2);
});
document.querySelector('#addViewInsertQuestionRowAbove').addEventListener('click', function() {
questionAnswerAddQuestionTable.insertRowAbove();
});
document.querySelector('#addViewInsertQuestionColumnLeft').addEventListener('click', function() {
questionAnswerAddQuestionTable.insertColumnLeft();
});
document.querySelector('#addViewInsertQuestionColumnRight').addEventListener('click', function() {
questionAnswerAddQuestionTable.insertColumnRight();
});
document.querySelector('#addViewDeleteQuestionRow').addEventListener('click', function() {
questionAnswerAddQuestionTable.deleteRow();
});
document.querySelector('#addViewDeleteQuestionColumn').addEventListener('click', function() {
questionAnswerAddQuestionTable.deleteColumn();
});
document.querySelector('#addViewDeleteQuestionTable').addEventListener('click', function() {
questionAnswerAddQuestionTable.deleteTable();
});
}
if ($("#questionAnswer-edit-question-editor-container") && $("#questionAnswer-edit-question-editor-container").length > 0 ) {
var snowEditViewQuestion = new Quill("#questionAnswer-edit-question-editor-container", { 
theme: 'snow',
modules: {
table: true,
}
});
var questionAnswerEditQuestionTable=snowEditViewQuestion.getModule('table');
document.querySelector('#editViewInsertQuestionTable').addEventListener('click', function() {
questionAnswerEditQuestionTable.insertTable(2, 2);
});
document.querySelector('#editViewInsertQuestionRowAbove').addEventListener('click', function() {
questionAnswerEditQuestionTable.insertRowAbove();
});
document.querySelector('#editViewInsertQuestionColumnLeft').addEventListener('click', function() {
questionAnswerEditQuestionTable.insertColumnLeft();
});
document.querySelector('#editViewInsertQuestionColumnRight').addEventListener('click', function() {
questionAnswerEditQuestionTable.insertColumnRight();
});
document.querySelector('#editViewDeleteQuestionRow').addEventListener('click', function() {
questionAnswerEditQuestionTable.deleteRow();
});
document.querySelector('#editViewDeleteQuestionColumn').addEventListener('click', function() {
questionAnswerEditQuestionTable.deleteColumn();
});
document.querySelector('#editViewDeleteQuestionTable').addEventListener('click', function() {
questionAnswerEditQuestionTable.deleteTable();
});
}
if ($("#questionAnswer-add-answer-editor-container") && $("#questionAnswer-add-answer-editor-container").length > 0 ) {
var snowAddViewAnswer = new Quill("#questionAnswer-add-answer-editor-container", { 
theme: 'snow',
modules: {
table: true,
}
});
var questionAnswerAddAnswerTable=snowAddViewAnswer.getModule('table');
document.querySelector('#addViewInsertAnswerTable').addEventListener('click', function() {
questionAnswerAddAnswerTable.insertTable(2, 2);
});
document.querySelector('#addViewInsertAnswerRowAbove').addEventListener('click', function() {
questionAnswerAddAnswerTable.insertRowAbove();
});
document.querySelector('#addViewInsertAnswerColumnLeft').addEventListener('click', function() {
questionAnswerAddAnswerTable.insertColumnLeft();
});
document.querySelector('#addViewInsertAnswerColumnRight').addEventListener('click', function() {
questionAnswerAddAnswerTable.insertColumnRight();
});
document.querySelector('#addViewDeleteAnswerRow').addEventListener('click', function() {
questionAnswerAddAnswerTable.deleteRow();
});
document.querySelector('#addViewDeleteAnswerColumn').addEventListener('click', function() {
questionAnswerAddAnswerTable.deleteColumn();
});
document.querySelector('#addViewDeleteAnswerTable').addEventListener('click', function() {
questionAnswerAddAnswerTable.deleteTable();
});
}
if ($("#questionAnswer-edit-answer-editor-container") && $("#questionAnswer-edit-answer-editor-container").length > 0 ) {
var snowEditViewAnswer = new Quill("#questionAnswer-edit-answer-editor-container", { 
theme: 'snow',
modules: {
table: true,
}
});
var questionAnswerEditAnswerTable=snowEditViewAnswer.getModule('table');
document.querySelector('#editViewInsertAnswerTable').addEventListener('click', function() {
questionAnswerEditAnswerTable.insertTable(2, 2);
});
document.querySelector('#editViewInsertAnswerRowAbove').addEventListener('click', function() {
questionAnswerEditAnswerTable.insertRowAbove();
});
document.querySelector('#editViewInsertAnswerColumnLeft').addEventListener('click', function() {
questionAnswerEditAnswerTable.insertColumnLeft();
});
document.querySelector('#editViewInsertAnswerColumnRight').addEventListener('click', function() {
questionAnswerEditAnswerTable.insertColumnRight();
});
document.querySelector('#editViewDeleteAnswerRow').addEventListener('click', function() {
questionAnswerEditAnswerTable.deleteRow();
});
document.querySelector('#editViewDeleteAnswerColumn').addEventListener('click', function() {
questionAnswerEditAnswerTable.deleteColumn();
});
document.querySelector('#editViewDeleteAnswerTable').addEventListener('click', function() {
questionAnswerEditAnswerTable.deleteTable();
});
}
if ($("#questionAnswer-add-summary-editor-container") && $("#questionAnswer-add-summary-editor-container").length > 0 ) {
var snowAddViewSummary = new Quill("#questionAnswer-add-summary-editor-container", { 
theme: 'snow',
modules: {
table: true,
}
});
var questionAnswerAddSummaryTable=snowAddViewSummary.getModule('table');
document.querySelector('#addViewInsertSummaryTable').addEventListener('click', function() {
questionAnswerAddSummaryTable.insertTable(2, 2);
});
document.querySelector('#addViewInsertSummaryRowAbove').addEventListener('click', function() {
questionAnswerAddSummaryTable.insertRowAbove();
});
document.querySelector('#addViewInsertSummaryColumnLeft').addEventListener('click', function() {
questionAnswerAddSummaryTable.insertColumnLeft();
});
document.querySelector('#addViewInsertSummaryColumnRight').addEventListener('click', function() {
questionAnswerAddSummaryTable.insertColumnRight();
});
document.querySelector('#addViewDeleteSummaryRow').addEventListener('click', function() {
questionAnswerAddSummaryTable.deleteRow();
});
document.querySelector('#addViewDeleteSummaryColumn').addEventListener('click', function() {
questionAnswerAddSummaryTable.deleteColumn();
});
document.querySelector('#addViewDeleteSummaryTable').addEventListener('click', function() {
questionAnswerAddSummaryTable.deleteTable();
});
}
if ($("#questionAnswer-edit-summary-editor-container") && $("#questionAnswer-edit-summary-editor-container").length > 0 ) {
var snowEditViewSummary = new Quill("#questionAnswer-edit-summary-editor-container", { 
theme: 'snow',
modules: {
table: true,
}
});
var questionAnswerEditSummaryTable=snowEditViewSummary.getModule('table');
document.querySelector('#editViewInsertSummaryTable').addEventListener('click', function() {
questionAnswerEditSummaryTable.insertTable(2, 2);
});
document.querySelector('#editViewInsertSummaryRowAbove').addEventListener('click', function() {
questionAnswerEditSummaryTable.insertRowAbove();
});
document.querySelector('#editViewInsertSummaryColumnLeft').addEventListener('click', function() {
questionAnswerEditSummaryTable.insertColumnLeft();
});
document.querySelector('#editViewInsertSummaryColumnRight').addEventListener('click', function() {
questionAnswerEditSummaryTable.insertColumnRight();
});
document.querySelector('#editViewDeleteSummaryRow').addEventListener('click', function() {
questionAnswerEditSummaryTable.deleteRow();
});
document.querySelector('#editViewDeleteSummaryColumn').addEventListener('click', function() {
questionAnswerEditSummaryTable.deleteColumn();
});
document.querySelector('#editViewDeleteSummaryTable').addEventListener('click', function() {
questionAnswerEditSummaryTable.deleteTable();
});
}
function removeQuestionAnswerSources(index) {
var ul = document.querySelector('#questionAnswerSources');
var liToRemove = ul.children[index];
if (liToRemove) {
ul.removeChild(liToRemove);
for (var i = index; i < ul.children.length; i++) {
updateQuestionAnswerPropertyIndex(ul.children[i], i);
}
}
}
function addQuestionAnswerSources() {
var ul = document.querySelector('#questionAnswerSources');
var newIndex = ul.childElementCount;
var li = document.createElement('li');
li.innerHTML = '<input type="text" class="form-control" id="sources' + newIndex + '"name="sources[' + newIndex + ']"/>'+
'<a href="#" onclick="removeQuestionAnswerSources(' + newIndex + ')" style="color: red;">Remove</a>';
ul.appendChild(li);
}
function updateContentField() {
if(snowAddViewQuestion){
var editorAddContent = snowAddViewQuestion.root.innerHTML;
document.getElementById("question").value = editorAddContent;
}
if(snowEditViewQuestion){
var editorEditContent = snowEditViewQuestion.root.innerHTML;
document.getElementById("question").value = editorEditContent;
}
if(snowAddViewAnswer){
var editorAddContent = snowAddViewAnswer.root.innerHTML;
document.getElementById("answer").value = editorAddContent;
}
if(snowEditViewAnswer){
var editorEditContent = snowEditViewAnswer.root.innerHTML;
document.getElementById("answer").value = editorEditContent;
}
if(snowAddViewSummary){
var editorAddContent = snowAddViewSummary.root.innerHTML;
document.getElementById("summary").value = editorAddContent;
}
if(snowEditViewSummary){
var editorEditContent = snowEditViewSummary.root.innerHTML;
document.getElementById("summary").value = editorEditContent;
}
 return true;
}
function updateQuestionAnswerPropertyIndex(li, newIndex){
var inputs = li.querySelectorAll('input, select');
inputs.forEach(function (input) {
var currentName = input.getAttribute('name');
var newName = currentName.replace(/\[\d+\]/, '[' + newIndex + ']');
input.setAttribute('name', newName);
});
}
