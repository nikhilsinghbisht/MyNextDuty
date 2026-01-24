import { Root } from "../layout/Root";
import Loader from "../components/common/Loader";
import AuthPage from "../page/AuthPage";
export const PrivateRouteConfig = [
  {
    key: "Root",
    component: Root,
    path: "/",
    children: [
      {
        key: "AuthPage",
        path: "",
        component: AuthPage,
      },
      {
        key: "Loader",
        path: "",
        component: Loader,
      },
    ],
  },
];

export const PublicRouteConfig = [];
