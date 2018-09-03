// https://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint'
  },
  env: {
    browser: true,
  },
  extends: [
    // https://github.com/vuejs/eslint-plugin-vue#priority-a-essential-error-prevention
    // consider switching to `plugin:vue/strongly-recommended` or `plugin:vue/recommended` for stricter rules.
    'plugin:vue/essential',
    // https://github.com/standard/standard/blob/master/docs/RULES-en.md
    'standard'
  ],
  // required to lint *.vue files
  plugins: [
    'vue'
  ],
  // add your custom rules here
  rules: {
    // 空格2个
    'indent': ['error', 4, {'SwitchCase': 1}],
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    'padded-blocks': [0, "always"],
    'no-undef': 0,
    'no-useless-escape': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0,
    'no-trailing-spaces': ["error", { "skipBlankLines": true }],
    'vue/no-parsing-error': ['error', {
      'x-invalid-end-tag': false
    }],
     'no-irregular-whitespace': ['error', { 'skipTemplates': true , "skipRegExps": true , "skipRegExps": true , "skipStrings": true}]
  }
}
