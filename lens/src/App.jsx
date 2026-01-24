import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { PrivateRouteConfig } from "./routes/RouteConfig";
import NavigationProvider from "../src/layout/NavigationProvider";

const App = () => {
  return (
    <BrowserRouter>
      <NavigationProvider>
        <Routes>
          {PrivateRouteConfig.map((route) => (
            <Route key={route.key} path={route.path} element={<route.component />}>
              {route.children?.map((child) => (
                <Route key={child.key} path={child.path} element={<child.component />} />
              ))}
            </Route>
          ))}
        </Routes>
      </NavigationProvider>
    </BrowserRouter>
  );
};

export default App;
