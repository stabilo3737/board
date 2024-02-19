
  const $listBtn = document.getElementById('listBtn');      //목록버튼
  const $updateBtn = document.getElementById('updateBtn');  //저장버튼

  //목록
  $listBtn.addEventListener('click',evt=>{
    location.href = '/boards';      // GET http://서버주소 or 서버도메인/boards
  }, false);

  //저장버튼 , 수정
  $updateBtn.addEventListener('click',evt=>{
    $editModal.showModal();
  });


///* 모달창 ---------------------------------------------*/
//  const $editModal = document.getElementById('editModal');    //삭제 모달창
//  const $clBtn = document.getElementById('clBtn');   //취소버튼 모달창
//  const $editItemBtn = document.getElementById('editItemBtn'); //저장버튼 모달창
//
//  $editModal.addEventListener('close',evt=>{
//    if($editModal.returnValue == 'ok'){
//      location.href = `/boards/${uid}/edit`; // GET http://서버주소 or 서버도메인/boards/아이디/edit
//    }
//  });
//
//  //취소버튼 모달창
//  $clBtn.addEventListener('click', evt=>{
//    $editModal.close('cancel');
//  });
//
//  //저장버튼 모달창
//  $editItemBtn.addEventListener('click', evt=>{
//    $editModal.close('ok');
//  });