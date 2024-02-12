import { useState } from "react";
import "./App.css";
import ProgressBar from "./ProgressBar";

function App() {
  const [progress, setProgress] = useState<number>(0);

  const buttonClickEvent = () => {
    const sse = new EventSource("http://localhost:8080/progress");
    sse.addEventListener("connect", (e) => {
      const { data: receivedConnectData } = e;

      setProgress((prevProgress) => {
        const newProgress = prevProgress + Number(receivedConnectData);
        if (newProgress > 100) {
          sse.close();
          console.log("stopped");
          return 0;
        }
        return newProgress;
      });
    });
  };

  return (
    <>
      <h1>SEE Example</h1>
      <div className="card">
        <button onClick={buttonClickEvent}>버튼</button>
        <ProgressBar progress={progress} />
      </div>
    </>
  );
}

export default App;
