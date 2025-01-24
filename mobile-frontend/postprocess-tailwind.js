#!/usr/bin/env node
const fs = require('fs');
const path = require('path');

const tailwindJsonPath = path.join(__dirname, 'tailwind.json');
const tailwindJson = require(tailwindJsonPath);

/*
 * Script to remove opacity directives from tailwind.json, 
 * as they are not supported by React Native and their automatic inclusion by the dev:tailwind script 
 * breaks the whole class (background, text, border colors)
 */
const processStyles = (styles) => {
    Object.keys(styles).forEach((key) => {
        if (styles[key].style) {
            Object.keys(styles[key].style).forEach((styleKey) => {
                if (styleKey === 'backgroundColor' && styles[key].style[styleKey].includes('var(--tw-bg-opacity')) {
                    styles[key].style[styleKey] = styles[key].style[styleKey].replace(' / var(--tw-bg-opacity, 1)', '');
                }
                if (styleKey === 'color' && styles[key].style[styleKey].includes('var(--tw-text-opacity')) {
                    styles[key].style[styleKey] = styles[key].style[styleKey].replace(' / var(--tw-text-opacity, 1)', '');
                }
                if ((styleKey === 'borderTopColor' || styleKey === 'borderRightColor' || styleKey === 'borderBottomColor' || styleKey === 'borderLeftColor') && styles[key].style[styleKey].includes('var(--tw-border-opacity')) {
                    styles[key].style[styleKey] = styles[key].style[styleKey].replace(' / var(--tw-border-opacity, 1)', '');
                }
            })
        }
    })
}

processStyles(tailwindJson);

fs.writeFileSync(tailwindJsonPath, JSON.stringify(tailwindJson, null, 2));
