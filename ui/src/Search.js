import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { faBook } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import "./Search.scss";

export default function Search() {
  const [fields, setFields] = useState([]);
  const [tagName, setTagName] = useState("");
  const [ascending, setAscending] = useState(false);
  const [orderBy, setOrderBy] = useState("");
  const [posts, setPosts] = useState([]);

  const getPosts = async (query) => {
    try {
      const response = await fetch(`http://localhost:8080/api/post?${query}`);
      if (response.status === 200) {
        return response.json();
      } else {
        throw new Error(`ERROR: ${response.Error}`);
      }
    } catch (err) {
      console.log(err);
    }
  };

  const onSubmit = (e) => {
    e.preventDefault();
    if (fields.includes(tagName)) return;
    setFields([...fields, tagName]);
    setTagName("");
  };

  const fetchPosts = (e) => {
    e.preventDefault();
    const tags = fields.toString().replace(/ /g, "%20");
    const direction = ascending ? "asc" : "desc";
    const query = `tag=${tags}&direction=${direction}&sortBy=${orderBy}`;
    getPosts(query).then((res) => renderPosts(res));
  };

  const renderPosts = (posts) => {
    console.log(posts)
  };
  return (
    <div
      style={{
        "background-color": "#485461",
        "background-image": "linear-gradient(315deg, #485461 0%, #28313b 74%)",
      }}
    >
      <section class="vh-100" style={{}}>
        <div class="container py-5 h-100">
          <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col col-xl-10">
              <div class="card" style={{ "border-radius": "15px" }}>
                <div class="card-body p-5">
                  <h6 class="mb-3">Blog Posts API</h6>

                  <form
                    class="d-flex justify-content-center align-items-center mb-4"
                    onSubmit={onSubmit}
                  >
                    <div class="form-outline flex-fill">
                      <input
                        type="text"
                        id="form3"
                        class="form-control form-control-lg"
                        value={tagName}
                        onChange={(e) => setTagName(e.target.value)}
                      />
                    </div>
                    <button type="submit" class="btn btn-primary btn-lg ms-2">
                      Add Tag
                    </button>
                  </form>

                  <ul class="list-group mb-0">
                    {fields.map((f) => (
                      <li class="list-group-item d-flex justify-content-between align-items-center border-start-0 border-top-0 border-end-0 border-bottom rounded-0 mb-2">
                        <div class="d-flex align-items-center">{f}</div>
                        <a
                          href="#!"
                          data-mdb-toggle="tooltip"
                          title="Remove item"
                          onClick={() => {
                            setFields(fields.filter((field) => field !== f));
                          }}
                        >
                          <FontAwesomeIcon icon={faXmark} />
                        </a>
                      </li>
                    ))}
                  </ul>

                  {fields.length > 0 && (
                    <form class="align-items-center m-4" onSubmit={fetchPosts}>
                      <div class="col-md-4 col-md-offset-4 mb-4">
                        <div class="checkbox switcher">
                          <label for="test">
                            <input
                              type="checkbox"
                              id="test"
                              value=""
                              onChange={() => setAscending(!ascending)}
                            />
                            <span>
                              <small></small>
                            </span>
                            <small>Sort Ascending</small>
                          </label>
                        </div>
                      </div>
                      <select
                        class="form-select mb-4"
                        onChange={(e) => setOrderBy(e.target.value)}
                      >
                        <option selected>Order By</option>
                        <option value="id">ID</option>
                        <option value="reads">Reads</option>
                        <option value="likes">Likes</option>
                        <option value="popularity">Popularity</option>
                      </select>
                      <button type="submit" class="btn btn-primary btn-lg ms-2">
                        Find Posts
                      </button>
                    </form>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
