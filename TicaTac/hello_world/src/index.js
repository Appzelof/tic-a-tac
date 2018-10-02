import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import registerServiceWorker from "./registerServiceWorker";
import Counter from "./counter";

ReactDOM.render(<Counter />, document.getElementById("root"));
registerServiceWorker();
