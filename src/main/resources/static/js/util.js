function searchForm() {
    const form = document.getElementById("form");
    form.children[1].disabled = (form.children[0].options[form.children[0].selectedIndex].value === "blank");
}

function deleteElement(id) {
    if (confirm('해당 항목을 삭제하겠습니까?')) location.href='/employee/delete/' + id;
}

function switchPage(flag) {
    if (confirm((flag ? '직원 관리' : '방문자 관리') + ' 페이지로 이동합니다.\n(주의 : 완료되지 않은 작업은 저장되지 않습니다.)')) {
        location.href = flag ? '/employee' : '/visitor'
    }
}