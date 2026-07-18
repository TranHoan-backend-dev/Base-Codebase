"use client"

import { BackIcon } from "@/components/icons";
import { ReactNode } from "react";
import { Button } from "@heroui/react";
import { TrashBin } from "@gravity-ui/icons";
import "./swe-toolbar.scss";
import "@/styles/custom/space.scss"

import { useTranslations } from "next-intl";
import viMessages from "@/messages/vi.json";

interface ToolbarProps {
    className?: string;
    title: string;
    rightSideActionButtons?: ReactNode;
    pickManyMode?: boolean; // chế độ khi người dùng click nhiều bản ghi
    numberOfPickedRecords?: number; // số bản ghi active
    onDeselect?: () => void;
    onDelete?: () => void;
}

export const SweToolbar = ({
    className = "",
    title = "",
    rightSideActionButtons,
    pickManyMode = false,
    numberOfPickedRecords,
    onDeselect,
    onDelete,
}: ToolbarProps) => {
    let translator: any = null;
    try {
        // eslint-disable-next-line react-hooks/rules-of-hooks
        translator = useTranslations("toolbar");
    } catch {
        translator = null;
    }

    const t = (key: string, values?: any): string => {
        if (translator) {
            try {
                return translator(key, values);
            } catch {
                // Fallback
            }
        }
        const defaultTranslations: Record<string, string> = viMessages.toolbar;
        let template = defaultTranslations[key] || key;
        if (values) {
            Object.keys(values).forEach(k => {
                template = template.replace(`{${k}}`, String(values[k]));
            });
        }
        return template;
    };

    return (
        <div className={`swe_toolbar flex items-center justify-between px-4 py-3 ${className}`}>
            {/* Toolbar trai */}
            <div className="toolbar_left flex gap-2 justify-center items-center">
                {pickManyMode && numberOfPickedRecords ? (
                    <div className="flex items-center gap-2">
                        <span>{t("selected", { count: numberOfPickedRecords })}</span>
                        <Button
                            variant="ghost"
                            className="text-danger px-0"
                            onPress={onDeselect}
                        >
                            {t("deselect")}
                        </Button>
                        <Button
                            variant="danger"
                            onPress={onDelete}
                        >
                            <TrashBin />
                            {t("delete")}
                        </Button>
                    </div>
                ) : (
                    <>
                        <BackIcon className="swe_back_btn" />
                        <span className="swe_toolbar_title">{title}</span>
                    </>
                )}
            </div>

            {/* Toolbar phai */}
            <div className="toolbar_right flex gap-2">
                {rightSideActionButtons}
            </div>
        </div>
    );
}