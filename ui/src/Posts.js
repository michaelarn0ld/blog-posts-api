import { useContext, useRef, useEffect } from "react";
import { Link } from "react-router-dom";
import PostsContext from "./PostsContext";
import "./Search.scss";
import Carousel from "react-elastic-carousel"
import { gsap } from "gsap";

export default function Posts() {
  const [posts, _] = useContext(PostsContext);
  const breakPoints = [
    { width: 1, itemsToShow: 1 },

    { width: 620, itemsToShow: 2 },

    { width: 1000, itemsToShow: 3 },
  ]
  const divRef = useRef();
  useEffect(() => {
    gsap.to(divRef.current, { opacity: 1, y: 0, duration: 0.5 }).delay(0.5);
  });

  return (
    <div
      style={{
        "background-color": "#485461",
        "background-image": "linear-gradient(315deg, #485461 0%, #28313b 74%)",
      }}
    >
      <section class="vh-100 postpad"
        ref={divRef}
        style={{ opacity: "0", transform: "translateY(-100px)" }}
      >
        <Carousel breakPoints={breakPoints}>
            {posts?.posts?.map((p, idx) =>
              (
                  <div class="card">
                    <h5 class="card-header">{p.author}</h5>
                    <div class="card-body">
                      <h5 class="card-title">ID: {p.id}</h5>
                      <h5 class="card-title">Likes: {p.likes}</h5>
                      <h5 class="card-title">Reads: {p.reads}</h5>
                      <h5 class="card-title">Popularity: {p.popularity}</h5>
                      <p class="card-text">
                        Tags: {p.tags.join(", ")}
                      </p>
                      <Link class="btn btn-primary" to="/">Go Home!</Link>
                    </div>
                  </div>
            ))}
        </Carousel>
      </section>
    </div>
  );
}
