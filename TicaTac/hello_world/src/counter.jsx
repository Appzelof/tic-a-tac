import React, { Component } from "react";

class Counter extends Component {
  state = {
    count: 0,
    imageUrl: "https://picsum.photos/200"
  };
  render() {
    return (
      <div>
        <img src={this.state.imageUrl} alt="" />
        <button onCick={this.formatCount()}>increment</button>
      </div>
    );
  }

  formatCount() {
    const { imageUrl } = this.state;
    return imageUrl;
  }
}

export default Counter;
