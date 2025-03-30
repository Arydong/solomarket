// 아이디 중복 검사
function checkDuplicateId() {
    const userId = $('#inputId').val();
    if (!userId) {
        $('#id_error_message').text('아이디를 입력해주세요!!!').css('color', 'red');
        return;
    }

    axios.get(`/user-api/check-id/${userId}`)
        .then(res => {
            if (res.data === true) {
                $('#id_error_message').text('사용 가능한 아이디입니다!!!').css('color', 'green');
            } else {
                $('#id_error_message').text('이미 사용 중인 아이디입니다!!!').css('color', 'red');
            }
        })
        .catch(err => {
            $('#id_error_message').text('서버 오류 발생!!!').css('color', 'red');
            console.error(err);
        });
}

// 닉네임 중복 검사 (쿼리 파라미터 방식)
function checkDuplicateNick() {
    const nickName = $('#inputNick').val();
    if (!nickName) {
        $('#nick_error_message').text('닉네임을 입력해주세요!!!').css('color', 'red');
        return;
    }

    axios.get(`/user-api/check-nick`, {
        params: { nickName: nickName }
    })
        .then(res => {
            if (res.data === true) {
                $('#nick_error_message').text('사용 가능한 닉네임입니다!!!').css('color', 'green');
            } else {
                $('#nick_error_message').text('이미 사용 중인 닉네임입니다!!!').css('color', 'red');
            }
        })
        .catch(err => {
            $('#nick_error_message').text('서버 오류 발생!!!').css('color', 'red');
            console.error(err);
        });
}

// 회원가입 요청
function checkRegistInfo() {
    const year = $('#inputYear').val();
    const month = $('#inputMonth').val().toString().padStart(2, '0');
    const day = $('#inputDay').val().toString().padStart(2, '0');

    const user = {
        userId: $('#inputId').val(),
        password: $('#inputPw1').val(),
        userName: $('#inputName').val(),
        nickName: $('#inputNick').val(),
        gender: $('#inputGender').val(),
        birth: `${year}-${month}-${day}`, // ✅ "yyyy-MM-dd" 형식 보장!
        email: $('#inputEmail').val(),
        phone: $('#inputPhone').val(),
        role: 'USER'
    };

    axios.post('/user-api/register', user)
        .then(() => {
            alert('환영합니다.');
            location.href = '/user/loginForm';
        })
        .catch(() => {
            alert('회원가입에 실패하셨습니다. 빈칸이 있는지 확인해주세요');
        });
}

// 입력 감지 리셋 함수들 (간단 메시지 제거용)
function checkUserId() {
    $('#id_error_message').text('');
}

function checkUserNick() {
    $('#nick_error_message').text('');
}

function checkUserName() {
    $('#name_error_message').text('');
}

function checkGender() {
    // 필요 시 처리
}

function checkUserEmail() {
    $('#email_error_message').text('');
}

function checkUserPhone() {
    $('#phone_error_message').text('');
}

function validateUserPw() {
    // 패스워드 유효성 검사 로직 자리
}

function checkUserPw2() {
    // 패스워드 확인 일치 체크 자리
}

// 생년월일 select 초기화
window.onload = function () {
    const yearSelect = document.getElementById('inputYear');
    const monthSelect = document.getElementById('inputMonth');
    const daySelect = document.getElementById('inputDay');

    for (let y = 1950; y <= 2025; y++) {
        yearSelect.options.add(new Option(y, y));
    }
    for (let m = 1; m <= 12; m++) {
        monthSelect.options.add(new Option(m, m));
    }
    for (let d = 1; d <= 31; d++) {
        daySelect.options.add(new Option(d, d));
    }
};

function setBirth() {
    // 선택된 년, 월, 일에서 조합 이미 처리됨
}
