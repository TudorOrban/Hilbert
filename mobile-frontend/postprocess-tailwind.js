#!/usr/bin/env node
const fs = require('fs');
const path = require('path');

const tailwindJsonPath = path.join(__dirname, 'tailwind.json');
const tailwindJson = require(tailwindJsonPath);

const processStyles = (styles) => {
    Object.keys(styles).forEach((key) => {
        if (styles[key].style) {
            Object.keys(styles[key].style).forEach((styleKey) => {
                console.log(`Processing ${key}: ${styles[key].style[styleKey]}`);
          
                if (styleKey === 'backgroundColor' && styles[key].style[styleKey].includes('var(--tw-bg-opacity')) {
                    styles[key].style[styleKey] = styles[key].style[styleKey].replace(' / var(--tw-bg-opacity, 1)', '');
                }
            })
        }
    })
}

processStyles(tailwindJson);

fs.writeFileSync(tailwindJsonPath, JSON.stringify(tailwindJson, null, 2));
