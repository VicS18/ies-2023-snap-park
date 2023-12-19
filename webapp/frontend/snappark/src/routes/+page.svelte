<script>
  /** @type {import('./$types').PageData} */
  export let data;
  import { userId } from '../stores/global';

  let currentValue = "Please login to access your parks!";

  const alter = () => {
      if ($userId !== null) {
          currentValue = "Welcome " + $userId + "!";
      } else {
          currentValue = "Please Login to your account.";
      }
  };

  userId.subscribe(value => {
      alter();
  });
</script>
<h1>{currentValue}</h1>

<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="https://scontent.fopo5-2.fna.fbcdn.net/v/t39.30808-6/280221928_2287061391457665_2442174087753516622_n.jpg?stp=dst-jpg_s960x960&_nc_cat=101&ccb=1-7&_nc_sid=783fdb&_nc_ohc=hsKo0_X1tAcAX_u5ytq&_nc_ht=scontent.fopo5-2.fna&oh=00_AfBcQ-9KnYlVTbQh8AuXPPvh8aFqdd0VTMvIfTcsHDjesg&oe=65861529" class="d-block w-100" alt="...">
    </div>
    <div class="carousel-item">
      <img src="https://onturtle.eu/wp-content/uploads/2023/03/OnTurtle-Parking-Camiones-La-Jonquera-scaled-1.jpeg" class="d-block w-100" alt="...">
    </div>
    <div class="carousel-item">
      <img src="https://cdn.vox-cdn.com/thumbor/okY54qvEzKcEa2RpTNu84xArEFI=/0x0:5464x3640/1200x675/filters:focal(2295x1383:3169x2257)/cdn.vox-cdn.com/uploads/chorus_image/image/72262173/GettyImages_1354859135__1_.0.jpg" class="d-block w-100" alt="...">
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
<style>
  #carouselExampleControls {
    max-height: 400px; /* Set your desired maximum height */
    overflow: hidden;
  }

  #carouselExampleControls img {
    object-fit: cover;
    width: 100%;
    height: 100%;
  }
</style>