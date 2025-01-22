module.exports = {
  root: true,
  extends: '@react-native',
  rules: {
    'no-unused-vars': ['warn', { 'vars': 'all', 'args': 'after-used', 'ignoreRestSiblings': false }],
    'quotes': ['error', 'single', 'double'],
  },
};
