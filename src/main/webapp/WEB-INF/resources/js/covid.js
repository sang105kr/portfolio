const $searchBtn = document.getElementById('searchBtn');	
$searchBtn.addEventListener("click",
	function(){
		const $startDt = document.getElementById('startDt');
		const $endDt = document.getElementById('endDt');
			
		//날짜입력체크
		if($startDt.value.length == 0 || $endDt.value.length == 0){
			alert('날짜를 입력하세요!');
			return;
		}
		
		//날짜값에 '-' 제거
		const startDt = $startDt.value.replaceAll('-','');
		const endDt	  =	$endDt.value.replaceAll('-','');		

		//조회
		search(startDt, endDt);
	}
,false);

function search(start,end){
	
	const url = `http://localhost:9080/portfolio/openapi/covid/${start}/${end}`;
	//const queryString = `?startDt=${start}&endDt=${end}`;
	//console.log(url+queryString);
	
	//ajax call
	ajaxCall.get(url,handler);
}

//응답온 json결과 처리
function handler(jsonObj) {
  const result = jsonObj.body.items.item;
  let result2 = '';
  let cnt = 0;
  for(const rec of result){
    result2 += `<tr>`;
    result2 += `  <th scope="row">${++cnt}</th>`;
    result2 += `  <td>${rec.createDt}</td>`;
    result2 += `  <td>${rec.decideCnt}</td>`;
    result2 += `  <td>${rec.clearCnt}</td>`;
    result2 += `  <td>${rec.examCnt}</td>`;
    result2 += `  <td>${rec.deathCnt}</td>`;
    result2 += `  <td>${rec.careCnt}</td>`;
    result2 += `  <td>${rec.resutlNegCnt}</td>`;
    result2 += `  <td>${rec.accExamCnt}</td>`;
    result2 += `  <td>${rec.accExamCompCnt}</td>`;
    result2 += `  <td>${rec.accDefRate}</td>`;
    result2 += `</tr>        `;
  }
  document.getElementById('result').innerHTML = result2;
}