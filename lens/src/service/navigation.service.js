let navigator = null;

/**
 * Called once when app initializes
 */
export const setNavigator = (navigateFn) => {
  navigator = navigateFn;
};

/**
 * Can be used anywhere (axios, redux, utils)
 */
export const navigate = (path, options = {}) => {
  if (!navigator) {
    console.warn("Navigator not initialized yet");
    return;
  }

  navigator(path, options);
};
