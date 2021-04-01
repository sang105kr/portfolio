<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <style>
    .wrapper img{
      max-height:250px;
    }
  </style>    
  <header>
    <div class="container container-h">
 <!--      <span class="copyright">그대의 커피향기속으로...</span> -->
 
	   <div class="wrapper">
	    <div id="carousel" class="carousel slide carousel-fade " data-bs-ride="carousel">
	      <div class="carousel-indicators">
	        <button type="button" data-bs-target="#carousel" data-bs-slide-to="0"  aria-current="true" aria-label="Slide 1"></button>
	        <button type="button" data-bs-target="#carousel" data-bs-slide-to="1" class="active" aria-label="Slide 2"></button>
	        <button type="button" data-bs-target="#carousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
	      </div>
	      <div class="carousel-inner" >
	        <div class="carousel-item">
	          <img src="https://cdn.pixabay.com/photo/2018/03/19/18/20/tea-time-3240766__340.jpg" class="d-block w-100" alt="...">
	          <div class="carousel-caption d-none d-md-block">
	            <h5>First slide label</h5>
	            <p>Some representative placeholder content for the first slide.</p>
	          </div>
	        </div>
	        <div class="carousel-item active">
	          <img src="https://cdn.pixabay.com/photo/2017/03/17/10/29/coffee-2151200__340.jpg" class="d-block w-100" alt="...">
	          <div class="carousel-caption d-none d-md-block">
	            <h5>Second slide label</h5>
	            <p>Some representative placeholder content for the second slide.</p>
	          </div>
	        </div>
	        <div class="carousel-item">
	          <img src="https://cdn.pixabay.com/photo/2016/11/29/06/17/black-coffee-1867753__340.jpg" class="d-block w-100" alt="...">
	          <div class="carousel-caption d-none d-md-block">
	            <h5>Third slide label</h5>
	            <p>Some representative placeholder content for the third slide.</p>
	          </div>
	        </div>
	      </div>
	      <button class="carousel-control-prev" type="button" data-bs-target="#carousel"  data-bs-slide="prev">
	        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	        <span class="visually-hidden">Previous</span>
	      </button>
	      <button class="carousel-control-next" type="button" data-bs-target="#carousel"  data-bs-slide="next">
	        <span class="carousel-control-next-icon" aria-hidden="true"></span>
	        <span class="visually-hidden">Next</span>
	      </button>
	    </div>
	  </div>
    </div>
	  <script>
	  	window.addEventListener('load',()=>{
		    const myCarousel = document.querySelector('#carousel')
		    const carousel = new bootstrap.Carousel(myCarousel, {
		      interval: 5000,
		      wrap: true,
		      touch: true
		    })
		  });
	  </script>    
  </header>