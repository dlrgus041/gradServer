function func1() {
    const form = document.getElementById("form");
    form.children[1].disabled = (form.children[0].options[form.children[0].selectedIndex].value === "blank");
}
function func2(id) {if (confirm('해당 항목을 삭제하겠습니까?')) location.href='/employee/delete/' + id;}