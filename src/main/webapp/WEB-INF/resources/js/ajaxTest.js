'use strict';

const list = evt => {
  const url = 'http://localhost:9080/portfolio/rboard/757';
  fetch(url, {
      method: 'GET',
      headers: { 'Accept': 'application/json' }
    })
      .then(response => response.json() )
      .then(json => handler(json))
      .catch(error => console.error(error));		
}

listAll.addEventListener('click',evt=>{

	list(evt);

});

function handler(json){
	const rboardList = json.rtmsg.list;
	
	let $listHTML = '';
	$listHTML += `<table>`;
	$listHTML += `  <tr>`;
	$listHTML += `    <td>no</td>`;
	$listHTML += `    <td>id</td>`;
	$listHTML += `    <td>nickname</td>`;
	$listHTML += `    <td>cdate</td>`;
	$listHTML += `    <td>rcontent</td>`;
	$listHTML += `  </tr>`;
	/*
	for(let i=0; i < rboardList.length; i++ ){
		$listHTML += `  <tr>`;
		$listHTML += `    <td>${rboardList[i].rnum}</td>`;
		$listHTML += `    <td>${rboardList[i].rid}</td>`;
		$listHTML += `    <td>${rboardList[i].rnickname}</td>`;
		$listHTML += `    <td>${rboardList[i].rcdate}</td>`;
		$listHTML += `    <td>${rboardList[i].rcontent}</td>`;
		$listHTML += `  </tr>`;
	}
	*/
	rboardList.forEach(item => {
		$listHTML += `  <tr>`;
		$listHTML += `    <td>${item.rnum}</td>`;
		$listHTML += `    <td>${item.rid}</td>`;
		$listHTML += `    <td>${item.rnickname}</td>`;
		$listHTML += `    <td>${item.rcdate}</td>`;
		$listHTML += `    <td>${item.rcontent}</td>`;
		$listHTML += `  </tr>`;
	});

	$listHTML += `</table>`;
	
	//items.insertAdjacentHTML('afterbegin',$listHTML);
	items.innerHTML = $listHTML;
}

