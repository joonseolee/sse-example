import React, { useEffect } from "react";

interface ProgressBarProps {
  progress: number;
}

const ProgressBar: React.FC<ProgressBarProps> = ({ progress }) => {
  useEffect(() => {}, [progress]);

  return (
    <div>
      <div
        style={{
          width: `${progress}%`,
          height: "20px",
          backgroundColor: "blue",
          transition: "width 1s",
        }}
      />
    </div>
  );
};

export default ProgressBar;
