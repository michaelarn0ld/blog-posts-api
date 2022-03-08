import React, { useState } from "react";
import Search from "./Search";
import Posts from "./Posts";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import PostsContext from "./PostsContext";

export default function App() {
  const [posts, setPosts] = useState();
  return (
    <BrowserRouter>
      <PostsContext.Provider value={[posts, setPosts]}>
        <Routes>
          <Route path="/" element={<Search />} />
          <Route path="/posts" element={<Posts />} />
        </Routes>
      </PostsContext.Provider>
    </BrowserRouter>
  );
}
