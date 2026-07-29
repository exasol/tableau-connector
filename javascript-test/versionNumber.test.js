const { describe, expect, test } = require("@jest/globals");
const fs = require('fs');
const { XMLParser } = require('fast-xml-parser');
const xmlParser = new XMLParser({
    ignoreAttributes: false,
    attributeNamePrefix: ''
});

function readXmlFile(path) {
    const content = fs.readFileSync(path, 'utf8');
    return xmlParser.parse(content);
}

function readJsonFile(path) {
    const content = fs.readFileSync(path, 'utf8');
    return JSON.parse(content)
}

/**
 * @param {string} version the version to split
 */
function splitVersion(version) {
    return version.split('.').map(part => parseInt(part, 10));
}

/**
 * @param {string} a first version
 * @param {string} b second version
 * @returns {number} -1 if a < b, 1 if a > b, 0 if a == b
 */
function versionComparator(a, b) {
    const [partsA, partsB] = [splitVersion(a), splitVersion(b)];
    if (partsA.length !== partsB.length) {
        throw Error(`Invalid version numbers: "${a}" and "${b}"`);
    }
    for (let i = 0; i < partsA.length; i++) {
        if (partsA[i] > partsB[i]) {
            return 1;
        } else if (partsA[i] < partsB[i]) {
            return -1;
        } else {
            continue;
        }
    }
    return 0;
}

function getLatestChangelogVersion() {
    const files = fs.readdirSync('../doc/changes/');
    const versions = files.map(f => f.match(/changes_([0-9.]+)\.md/))
        .filter(match => match !== null).map(match => match[1])
        .sort(versionComparator)
    return versions[versions.length - 1]
}

describe("Split version", () => {
    test("Splits version correctly", () => {
        expect(splitVersion("1.0.0")).toEqual([1, 0, 0]);
        expect(splitVersion("2.10.3")).toEqual([2, 10, 3]);
        expect(splitVersion("0.1.5")).toEqual([0, 1, 5]);
    })
})

describe("Version comparator", () => {
    test("Compares versions correctly", () => {
        expect(versionComparator("1.0.0", "1.0.1")).toBe(-1);
        expect(versionComparator("1.0.1", "1.0.0")).toBe(1);
        expect(versionComparator("1.0.0", "1.0.0")).toBe(0);
        expect(versionComparator("1.0.9", "1.0.10")).toBe(-1);
        expect(versionComparator("1.2.0", "1.10.0")).toBe(-1);
        expect(versionComparator("2.0.0", "1.10.0")).toBe(1);
    })
})

const changelogVersion = getLatestChangelogVersion();
describe(`Latest version number ${changelogVersion}`, () => {

    test("Parent pom", () => {
        const pom = readXmlFile("../pom.xml");
        const pomRevision = pom.project.properties.revision;
        expect(pomRevision).toBe(changelogVersion);
    });

    test("NPM package.json", () => {
        const packageJson = readJsonFile("package.json");
        const packageJsonVersion = packageJson.version;
        expect(packageJsonVersion).toBe(changelogVersion);
    });

    ["jdbc", "odbc"].forEach(type => {
        test(`Manifest of ${type} driver`, () => {
            const manifest = readXmlFile(`../src/exasol_${type}/manifest.xml`);
            const manifestVersion = manifest["connector-plugin"]["plugin-version"];
            expect(manifestVersion).toBe(changelogVersion);
        });
    });
});
