import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root"));
// 找到 HTML 中 id 為 'root' 的元素，然後在這個元素中建立一個 React 應用的根容器，這樣我們就可以在這裡(root)渲染我們的 React 元件

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
